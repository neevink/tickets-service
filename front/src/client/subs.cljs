(ns client.subs
  (:require
   [re-frame.core :as re-frame :refer [reg-sub]]))

(reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(reg-sub
 ::current-ticket
 (fn [db _]
   (get-in db [:current-ticket])))

(reg-sub
 ::tickets
 (fn [db _]
   (get-in db [:tickets])))

(reg-sub
 ::ticket-by-id
 (fn [db [_ id]]
   (get-in db [:tickets id])))

(reg-sub
 ::event-by-id
 (fn [db [_ id]]
   (get-in db [:events id])))

(reg-sub
 ::mode
 (fn [db [_]]
   (get db :mode)))

(reg-sub
 ::form
 (fn [db [_]]
   (get db :form)))

(reg-sub
 ::form-prop
 (fn [db [_ prop-path]]
   (get-in db (into [:form] prop-path))))

(reg-sub
 ::event-form-prop
 (fn [db [_ prop-path]]
   (get-in db (into [:event-form] prop-path))))

(reg-sub
 ::form-valid?
 (fn [db [_]]
   (let [form (get db :form-valid)]
     (or (nil? form)
         (and (or (vector? form) (seq? form)) (empty? form))))))

(reg-sub
 ::events-form-valid?
 (fn [db [_]]
   (let [form (get db :events-form-valid)]
     (or (nil? form)
         (and (or (vector? form) (seq? form)) (empty? form))))))

(reg-sub
 ::form-path-invalid-message
 (fn [db [_ prop-path]]
   (first (vec (filter
                (fn [error-mp]
                  (= prop-path (:path error-mp)))
                (get db :form-valid))))))

(reg-sub
 ::event-form-path-invalid-message
 (fn [db [_ prop-path]]
   (first (vec (filter
                (fn [error-mp]
                  (= prop-path (:path error-mp)))
                (get db :event-form-valid))))))

(reg-sub
 ::toggle-new
 (fn [db [_]]
   (get db :toggle-new)))

(reg-sub
 ::toggle-delete
 (fn [db [_]]
   (get db :toggle-delete)))

(reg-sub
 ::current-page
 (fn [db [_]]
   (get-in db [:paging :current-page])))

(reg-sub
 ::last-page
 (fn [db [_]]
   (get-in db [:paging :last-page])))

(reg-sub
 ::toggle-change
 (fn [db [_]]
   (get-in db [:toggle-change])))

(reg-sub
 ::ticket-update
 (fn [db [_]]
   (get-in db [:ticket :update-id])))

(reg-sub
 ::page-size
 (fn [db [_]]
   (get-in db [:paging :page-size])))

(reg-sub
 ::tickets-on-page
 (fn [db [_]]
   (let  [page (get-in db [:paging :current-page])
          page-size (get-in db [:paging :page-size])]
     (into {} (take page-size (drop
                               (* page-size (dec page))
                               (get-in db [:tickets])))))))

(reg-sub
 ::events-on-page
 (fn [db [_]]
   (let  [page (get-in db [:paging :current-page])
          page-size (get-in db [:paging :page-size])]
     (into {} (take page-size (drop
                               (* page-size (dec page))
                               (get-in db [:events])))))))

(reg-sub
 ::filters
 (fn [db [_ prop]]
   (get-in db [:filters prop])))

(reg-sub
 ::ticket-edit-prop
 (fn [db [_ prop]]
   (get-in db (into [:ticket :edit :path] prop))))
