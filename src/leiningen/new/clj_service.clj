(ns leiningen.new.clj-service
    (:require [clojure.string :refer [join split]])
    (:use
      [leiningen.new.templates :only [renderer sanitize year ->files]]))

(defn user-prompt
  "Gets input from stdin and applies parse-fn function to it
   and returns the result"
  [msg]
  (println msg)
  (read-line))

(defn clj-service
  "Create a template for a new service"
  [name]
  (let [root-path (user-prompt "What should be the root directory? (For e.g. passing com/sdslabs will generate everything in src/com/sdslabs):")
        port (user-prompt "What should be the port number? (For e.g. 40464")
        data {:name name
              :root root-path
              :dot-path (join "." (split root-path #"/"))
              :sanitized (sanitize name)
              :port port
              :year (year)}
        render #((renderer "clj-service") % data)]

       (->files data
          [".gitignore"  (render "gitignore")]
          ["project.clj" (render "project.clj.tmpl")]
          ["README.md"   (render "README.md")]
          ["src/{{root}}/{{sanitized}}/service.clj" (render "service.clj")]
          ["test/{{root}}/test/{{sanitized}}/service.clj" (render "service_test.clj")]
          ["src/{{root}}/{{sanitized}}/resources.clj" (render "resources.clj")]
          ["resources/log4j.properties" (render "log4j.properties")])
       (println (format "Success! Template for service %s created" name))))
