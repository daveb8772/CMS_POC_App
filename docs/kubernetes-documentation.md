# Kubernetes Docs


## Kubernetes Architecture
![K8s_Cluster_Diagram](http://www.plantuml.com/plantuml/png/TP11QWCn34NtFeNm5Ng1XAnAQGdDeekIXJ9MCqDioyXooL3cxdKw0-CKeftU_zZ_QvCLHKlm9b-6c50mW1yYwPW8MBHdOo8x0ljIJYHLe0mxNxAIMFWnK6VMN_76uAqk5Z33ibVBCf_R_JCSIBv9ZYiyT3svuk_OZgCA-mhhAn7FhUXGyOIPm99splxngOdnmN9fOynx6-DMn9F--mzhQO7t4dK8TBntmrxuEgxmIp0Gs3sxFDzWdOv3uaXHmOOHKuBhqtoDSPlI8sVYh6UXR1yQX0DfJwMnJ4lroMmekXByBm00)
<details>
<summary>K8s_Cluster_Diagram</summary>

```
@startuml Simplified Kubernetes Deployment
skinparam linetype ortho

node "Kubernetes Cluster" {
node "Master Node" as Master {
[API Server]
[Scheduler]
[Controller Manager]
database "etcd"
}
node "Worker Node" as Worker {
[Kubelet]
[Container Runtime]
[Kube Proxy]
frame "Pods" {
component "myapp x3" as MyApp
component "postgres"
component "prometheus"
}
}
}

@enduml

```
</details>



![Detailed_K8s_Cluster_Diagram](http://www.plantuml.com/plantuml/png/XPA_3jem4CPtFyKf3y3IhK88H9UgQeNAqA7gOE8ZiV0Vwtn6H1NlNiUOa4P5zPIxxtV-xC_PHfR4oHeHBze5ITA2qGvv20YUkFT2EAyGcY_fX9G5ZB0tAJ9I0xy5vFNGlygn1zzoqO2CKEkCrFLpT_WCHwGhqgyHoELBExOzgcIozEhjlMFo9ZVpPavsTUxLdb0bMPvaH6YGMzMK_drsjX-UBlEprNfsjl5s1ddXBhNBsFVaM5iivlNxxq4uaByDHIzVathESMRxWrUnX3LRhRV1EtGCZHra273xy8Xjs8NmZWm-SaSOcxS0UOlSOveXznh7NOZn1LQh3KnfmqTOh_EyJKwtaZ5kDXDKS_uFLOFx5rAbWjNS2cRBOow9Sl6YAmp63qkz95jjt5btYSeMmdb68Dtr3Fuy_bIPkUIvbisOJ3PJ4RHZ9Daoz4Yu67aQVogsgE2gvNC7iKMdaZL_0000)
<details>
<summary>Detailed_K8s_Cluster_Diagram</summary>
  
```
@startuml Detailed Kubernetes Deployment Interaction
skinparam linetype ortho

node "Kubernetes Cluster" {
node "Master Node" as Master {
[API Server] as API
[Scheduler]
[Controller Manager] as Controller
database "etcd"
}
node "Worker Node" as Worker {
[Kubelet]
[Container Runtime] as Runtime
[Kube Proxy] as Proxy
frame "Pods" {
component "myapp x3" as MyApp
component "postgres"
component "prometheus"
}
}
}

API ..> Scheduler : <<communicates>>
API ..> Controller : <<communicates>>
API ..> Kubelet : <<communicates>>
Kubelet ..> Runtime : <<manages>>
Kubelet ..> MyApp : <<deploys>>
Kubelet ..> Proxy : <<configures>>

note right of API : kubectl commands interact here
note right of MyApp : Deployed via kubectl

@enduml

```

</details>
