(ns merjemterud.export
  (:require
    [merjemterud.main :as main]
    [powerpack.export :as export]))

(defn ^:export export! [& args]
  (-> main/config
      (assoc :site/base-url "https://merjemterud.no")
      export/export!))
