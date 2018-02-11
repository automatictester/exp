#!/usr/bin/env bash

if [ "$AWS_PROFILE" != "private" ]; then
   echo "AWS_PROFILE not set correctly"
   exit 1
fi

JENKINS_MASTER_INSTANCE_ID=$(aws ec2 describe-instances --filters 'Name=tag:Name,Values=Jenkins Master' 'Name=instance-state-name,Values=running' --query 'Reservations[*].Instances[*].InstanceId' --output text)
echo "Jenkins master instance ID: ${JENKINS_MASTER_INSTANCE_ID}"

echo -n 'Stopping Jenkins master... '
aws ec2 stop-instances --instance-ids ${JENKINS_MASTER_INSTANCE_ID} --output text >> /dev/null
echo 'done!'

JENKINS_SLAVE_INSTANCE_ID=$(aws ec2 describe-instances --filters 'Name=tag:Name,Values=jenkins slave - t2.small' 'Name=instance-state-name,Values=running' --query 'Reservations[*].Instances[*].InstanceId' --output text)
echo "Jenkins slave instance ID: ${JENKINS_SLAVE_INSTANCE_ID}"

echo -n 'Terminating Jenkins slave, if any... '
aws ec2 terminate-instances --instance-ids ${JENKINS_SLAVE_INSTANCE_ID} --output text &> /dev/null
echo 'done!'
