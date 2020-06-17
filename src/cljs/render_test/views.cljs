(ns render-test.views
  (:require
   [re-frame.core :as re-frame]
   [render-test.subs :as subs]
   ))

(defn td [content]
  [:td content])

(defn tr [row]
  [:tr
   ^{:key (str (:id row) "-" (:field-1 row))}
   [td (:field-1 row)]
   ^{:key (str (:id row) "-" (:field-2 row))}
   [td (:field-2 row)]
   ^{:key (str (:id row) "-" (:field-3 row))}
   [td (:field-3 row)]
   ^{:key (str (:id row) "-" (:field-4 row))}
   [td (:field-4 row)]])

(re-frame/reg-event-fx
  ::increment
  (fn [{db :db} _]
    {:db (update db :counter inc)}))

(re-frame/reg-event-fx
 ::toggle
 (fn [{db :db} _]
   {:db (update db :show? not)}))

(re-frame/reg-sub
 ::subs/show?
 (fn [db _]
   (:show? db)))

(re-frame/reg-sub
  ::counter
  (fn [db _]
    (:counter db)))

(defn main-panel []
  [:<> 
   [:button {:on-click #(re-frame/dispatch [::toggle])} "show"]
   (when @(re-frame/subscribe [::subs/show?])
     (let [name (re-frame/subscribe [::subs/name])
           counter (re-frame/subscribe [::counter])]
       [:div
        [:h1 {:on-click #(re-frame/dispatch [::increment])} "Hello from " @name @counter]
        (into [:table]
              (for [row @(re-frame/subscribe [::subs/rows])]
                ^{:key (:id row)}
                [tr row]))]))])
