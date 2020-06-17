(ns render-test.db)

(def default-db
  {:name "re-frame"
   :counter 0
   :rows (->> (range 5000)
              (interleave (cycle [:id :field-1 :field-2 :field-3 :field-4]))
              (partition 10)
              (map #(apply hash-map %)))})
