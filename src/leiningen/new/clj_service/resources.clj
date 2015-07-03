(ns {{dot-path}}.{{name}}.resources
  (:require
    [ring.util.http-response :as http]
    [compojure.api.sweet :refer [defroutes* GET*]]
    [compojure.api.meta]))

(defroutes* v1_routes
  (GET*
    "/{{name}}"
    []
    :new-relic-name "/v1/{{name}}"
    :summary "Dummy Route to index"
    :description
    "<p>Add a description here</p>"
    (http/ok "OK")))
