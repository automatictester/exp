#!/usr/bin/env bash

PUBLIC_IP=$(dig +short myip.opendns.com @resolver1.opendns.com)
echo "Your public IP: ${PUBLIC_IP}"

JENKINS_MASTER_INSTANCE_ID=$(aws ec2 describe-instances --filters 'Name=tag:Name,Values=Jenkins Master' 'Name=instance-state-name,Values=stopped' --output text --query 'Reservations[*].Instances[*].InstanceId')
echo "Jenkins master instance ID: ${JENKINS_MASTER_INSTANCE_ID}"

JENKINS_MASTER_SECURITY_GROUP_ID=$(aws ec2 describe-security-groups --filters 'Name=tag:Name,Values=Jenkins Master' --query 'SecurityGroups[*].GroupId' --output text)
echo "Jenkins master security group ID: ${JENKINS_MASTER_SECURITY_GROUP_ID}"

echo -n 'Setting Jenkins master security group to accept inbound connections only from your public IP... '
OLD_SSH_CIDR=$(aws ec2 describe-security-groups --filters "Name=description,Values=SSH and Jenkins HTTPS from my public IP only" --query 'SecurityGroups[*].IpPermissions[?FromPort==`22`].IpRanges[*].CidrIp' --output text)
aws ec2 revoke-security-group-ingress --group-id ${JENKINS_MASTER_SECURITY_GROUP_ID} --protocol tcp --port 22 --cidr ${OLD_SSH_CIDR}
aws ec2 authorize-security-group-ingress --group-id ${JENKINS_MASTER_SECURITY_GROUP_ID} --protocol tcp --port 22 --cidr "${PUBLIC_IP}/32"

OLD_HTTPS_CIDR=$(aws ec2 describe-security-groups --filters "Name=description,Values=SSH and Jenkins HTTPS from my public IP only" --query 'SecurityGroups[*].IpPermissions[?FromPort==`8081`].IpRanges[*].CidrIp' --output text)
aws ec2 revoke-security-group-ingress --group-id ${JENKINS_MASTER_SECURITY_GROUP_ID} --protocol tcp --port 8081 --cidr ${OLD_HTTPS_CIDR}
aws ec2 authorize-security-group-ingress --group-id ${JENKINS_MASTER_SECURITY_GROUP_ID} --protocol tcp --port 8081 --cidr "${PUBLIC_IP}/32"

echo 'done!'

echo -n 'Starting Jenkins master... '
aws ec2 start-instances --instance-ids ${JENKINS_MASTER_INSTANCE_ID} --output text >> /dev/null
echo 'done!'

URL=$(aws ec2 describe-instances --filters 'Name=tag:Name,Values=Jenkins Master' "Name=instance-id,Values=${JENKINS_MASTER_INSTANCE_ID}" --output text --query 'Reservations[*].Instances[*].NetworkInterfaces[*].PrivateIpAddresses[*].Association.PublicDnsName')
echo -e "URL:\nhttps://${URL}:8081"
