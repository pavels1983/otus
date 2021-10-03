# LAB2

# create configmap & secrets
kubectl create -f ./pg/pg_props.yaml
# kubectl describe configmap pg-config
# kubectl describe secrets pg-secrets

# create postgres db via helm
helm install postgres ./helm/postgres

# init load db (migrations)
kubectl apply -f ./pg/init_db.yaml

# create crud application
kubectl apply -f ./app/app.yaml

# create ingress
kubectl apply -f ./app/ingress.yaml

# test
curl -H 'Host: arch.homework' http://192.168.223.1/api/users
newman run ./User API.postman_collection.json



# delete all
kubectl delete ingress lab2-ingress
kubectl delete -f ./app/app.yaml
kubectl delete -f ./db/init_db.yaml
helm delete postgres
kubectl delete -f ./pg/pg_props.yaml
