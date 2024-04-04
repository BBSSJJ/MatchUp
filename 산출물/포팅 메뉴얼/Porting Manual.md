# 📖 Porting Manual
## 1. NginX &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/NginX-v1.18.0-blue?logo=nginx&logoColor=%23009639)
1. Nginx 설치
```
sudo apt-get install -y nginx
```
2. Config 설정
```
server {

        root /var/www/html;

        index index.html;

        server_name matchup.site;

        location / {
                proxy_pass http://10.98.39.179:9006;
                proxy_http_version 1.1;                                      # HTTP 1.1 사용
                proxy_set_header Upgrade $http_upgrade;                      # Upgrade 헤더를 전달하여 웹소켓 핸드셰이크를 지원
                proxy_set_header Connection "upgrade";                       # Connection 헤더 설정
                proxy_set_header Host $host;                                 # 프록시될 요청에 Host 헤더 추가
                proxy_set_header X-Real-IP $remote_addr;                     # 실제 클라이언트 IP를 전달
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for; # 포워딩된 요청에 대한 정보 추가
                proxy_set_header X-Forwarded-Proto $scheme;                  # 사용된 프로토콜 (http 또는 https) 전달
        }

        location /api {
                proxy_pass http://10.97.3.124:9000;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_read_timeout 86400;                                    # 세션타임 아웃(24시간)
        }

        location /openvidu {
                proxy_pass http://localhost:5443;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_read_timeout 86400;
        }

        location /grafana/ {
                proxy_pass http://10.109.3.177:3000/;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_read_timeout 86400;
        }

        location /file {
                proxy_pass http://10.105.248.185:8001;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_read_timeout 86400;

                client_max_body_size 500M;
        }

         location /swagger/ {
                proxy_pass http://10.97.33.25:8002/;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_read_timeout 86400;
        }

        listen [::]:443 ssl ipv6only=on; # managed by Certbot
        listen 443 ssl; # managed by Certbot
        ssl_certificate /etc/letsencrypt/live/matchup.site/fullchain.pem; # managed by Certbot
        ssl_certificate_key /etc/letsencrypt/live/matchup.site/privkey.pem; # managed by Certbot
        include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
        ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}

server {

        if ($host = matchup.site) {
            return 301 https://$host$request_uri;
        }


        listen 80 default_server;
        listen [::]:80 default_server;

        server_name matchup.site;
        return 404; # managed by Certbot

}
```

<br><br>

## 2. Jenkins&nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/jenkins-v2.441-blue?logo=jenkins&logoColor=%23D24939)
1. Jenkins 설치
```
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt-get update

sudo apt-get install jenkins
```

<br><br>

## 3. Kubernetes &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/Kubernetes-v1.29.2-blue?logo=kubernetes)

1. Docker Engine 설치
```
# Add Docker's official GPG key:
sudo apt-get update

sudo apt-get install ca-certificates curl

sudo install -m 0755 -d /etc/apt/keyrings

sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc

sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update

# install the Docker Packages:
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```
2. cri-dockerd 설치
```
wget https://github.com/Mirantis/cri-dockerd/releases/download/v0.3.11/cri-dockerd-0.3.11.amd64.tgz

tar -xvzf cri-dockerd-0.3.11.amd64.tgz

mv cri-dockerd/cri-dockerd /usr/local/bin

cri-dockerd
```

3. Kubernetes 설치
```
sudo apt update

sudo apt install -y apt-transport-https ca-certificates curl
 
curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.29/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
 
echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.29/deb/ /' | sudo tee /etc/apt/sources.list.d/kubernetes.list
 
sudo apt update 
 
sudo apt install -y kubeadm kubelet kubectl
```

4. calico 설치
```
kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml
```

<br><br>

## 4. Grafana with Prometheus &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/Grafana-v10.4.0-blue?logo=grafana)&nbsp;![Static Badge](https://img.shields.io/badge/Prometheus-v2.50.1-blue?logo=prometheus)

1. Helm 설치
```
curl https://baltocdn.com/helm/signing.asc | gpg --dearmor | sudo tee /usr/share/keyrings/helm.gpg > /dev/null

sudo apt-get install apt-transport-https -y

echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/helm.gpg] https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list

sudo apt-get update
sudo apt-get install helm

helm repo add bitnami https://charts.bitnami.com/bitnami
```

2. Prometheus 설치

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

kubectl create namespace monitoring

helm install prometheus prometheus-community/prometheus --namespace prometheus
```

3. Grafana 설치
```
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

kubectl create namespace monitoring

helm install grafana grafana/grafana --namespace grafana

kubectl get secret --namespace monitoring grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
```

4. Grafana Config 수정
- config 파일 추출
```
helm get values grafana --namespace monitoring > /yaml/grafana-values.yaml
```
- config 파일 수정
#### `/yaml/grafana-values.yaml`
```
## Grafana's primary configuration
## NOTE: values in map will be converted to ini format
## ref: http://docs.grafana.org/installation/configuration/
##
grafana.ini:
  paths:
    data: /var/lib/grafana/
    logs: /var/log/grafana
    plugins: /var/lib/grafana/plugins
    provisioning: /etc/grafana/provisioning
  analytics:
    check_for_updates: true
  log:
    mode: console
  grafana_net:
    url: https://grafana.net
  server:
    domain: j10a405.p.ssafy.io                      <- 서버 도메인과
    root_url: '%(protocol)s://%(domain)s/grafana/'  <- root_url 설정하여 nginx 경로에 맞춤
```
- 수정된 config 파일 적용
```
helm upgrade grafana grafana/grafana --namespace monitoring -f grafana-values.yaml
```

<br><br>

## 4. 서버 설치
1. /yaml 폴더의 이름에 맞게 apply
```
kubectl apply -f [서버 파일 이름]
```

<br><br>
