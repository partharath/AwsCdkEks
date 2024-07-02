package com.myorg;

import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.eks.Cluster;
import software.amazon.awscdk.services.eks.KubernetesVersion;
import software.amazon.awscdk.services.rds.SubnetGroup;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;


public class AwsCdkEksStack extends Stack {
    public AwsCdkEksStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public AwsCdkEksStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);


        // Create a VPC for the EKS cluster
        Vpc vpc = Vpc.Builder.create(this, "VPC")
                .maxAzs(3)
                .build();

        SubnetGroup publicSubnetGroup = SubnetGroup.Builder.create(this, "MyPublicSubnetGroup")
                .vpc(vpc)
                .description("Subnet group for public subnets")
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .build();



        // Create the EKS cluster
        Cluster cluster = Cluster.Builder.create(this, "EKSCluster")
                .clusterName("my-eks-cluster")
                .version(KubernetesVersion.V1_30)
                .vpc(vpc)
                .defaultCapacity(2)
                .defaultCapacityInstance(InstanceType.of(InstanceClass.T2,InstanceSize.MICRO))
                .build();
    }
}
