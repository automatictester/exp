#!/usr/bin/env bash

PUBLIC_IP=$(dig +short myip.opendns.com @resolver1.opendns.com)
echo "Your public IP: ${PUBLIC_IP}"

JENKINS_MASTER_INSTANCE_ID=$(aws ec2 describe-instances --filters 'Name=tag:Name,Values=Jenkins Master' 'Name=instance-state-name,Values=stopped' --output text --query 'Reservations[*].Instances[*].InstanceId')
echo "Jenkins master instance ID: ${JENKINS_MASTER_INSTANCE_ID}"

JENKINS_MASTER_SECURITY_GROUP_ID=$(aws ec2 describe-security-groups --filters 'Name=tag:Name,Values=Jenkins Master' --query 'SecurityGroups[*].GroupId' --output text)
echo "Jenkins master security group ID: ${JENKINS_MASTER_SECURITY_GROUP_ID}"

echo 'Setting Jenkins master security group to accept inbound connections only from your public IP ...'
aws ec2 update-security-group-rule-descriptions-ingress --group-id ${JENKINS_MASTER_SECURITY_GROUP_ID} --ip-permissions "[{\"IpProtocol\": \"tcp\", \"FromPort\": 22, \"ToPort\": 22, \"IpRanges\": [{\"CidrIp\": \"${PUBLIC_IP}/32\", \"Description\": \"SSH for admin\"}]}]" --output text
aws ec2 update-security-group-rule-descriptions-ingress --group-id ${JENKINS_MASTER_SECURITY_GROUP_ID} --ip-permissions "[{\"IpProtocol\": \"tcp\", \"FromPort\": 8081, \"ToPort\": 8081, \"IpRanges\": [{\"CidrIp\": \"${PUBLIC_IP}/32\", \"Description\": \"Jenkins HTTPS access\"}]}]" --output text
echo '... done'

echo 'Starting Jenkins master ...'
aws ec2 start-instances --instance-ids ${JENKINS_MASTER_INSTANCE_ID} --output text >> /dev/null
echo '... done'

URL=$(aws ec2 describe-instances --filters 'Name=tag:Name,Values=Jenkins Master' "Name=instance-id,Values=${JENKINS_MASTER_INSTANCE_ID}" --output text --query 'Reservations[*].Instances[*].NetworkInterfaces[*].PrivateIpAddresses[*].Association.PublicDnsName')
echo -e "URL:\nhttps://${URL}:8081"
