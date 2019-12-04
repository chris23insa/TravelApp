//package com.example.chris.travelorga_kth.network;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.Serializable;
//
//public abstract class GenericDaoImpl <T, PK extends Serializable> implements GenericDao<T, PK> {
//    private Scalingo server;
//
//    public GenericDaoImpl(Scalingo server) {
//        this.server = server;
//    }
//
//    // The endpoints must be defined in the children daos.
//    protected abstract String getCreateEndpoint();
//    protected abstract String getRetrieveEndpoint(PK entityId);
//    protected abstract String getUpdateEndpoint(PK entityId);
//    protected abstract String getDeleteEndpoint(PK entityId);
//
//    public void retrieve(PK id,
//                         ScalingoResponse.SuccessListener<T> successCallbackInterface,
//                         ScalingoResponse.ErrorListener errorCallbackInterface) {
//        JSONObject emptyRequestBody = new JSONObject();
//
//        server.getRequest(
//                this.getRetrieveEndpoint(id),
//                emptyRequestBody,
//                successCallbackInterface,
//                errorCallbackInterface
//        );
//    }
//
//    public void testtest() {
//        this.retrieve(2131L,
//                new ScalingoResponse.SuccessListener<Long>() {
//                 @Override
//                         public void onResponse(Long l) {
//
//                 }
//                },
//        new ScalingoResponse.ErrorListener() {
//            @Override
//            public void onError(error) {
//
//            }
//        });
//    }
//    public void create(T entity,
//                       ScalingoResponse.SuccessListener<T> successCallbackInterface,
//                       ScalingoResponse.ErrorListener errorCallbackInterface) throws JSONException {
//        JSONObject requestBody = entity.jsonify();
//
//        server.postRequest(
//                entity.getCreateEndpoint(),
//                requestBody,
//                successCallbackInterface,
//                errorCallbackInterface
//        );
//    }
//
////    public void retrieve(PK id) {
////        JSONObject requestBody = new JSONObject();
////
////        server.sendRequest(
////                ScalingoMethod.GET,
////                endpoints.get
////        )
////        return (T) getSession().get(type, id);
////    }
//
////    public PK create(T entity) {
////        JSONObject requestBody = entity.jsonify();
////
////        server.sendRequest(
////                ScalingoMethod.POST,
////                entity.getCreateEndpoint(),
////                requestBody,
////                new ScalingoResponse.SuccessListener<T>() {
////                    @Override
////                    public void onSuccess(entity) {
////
////                    }
////                },
////                new ScalingoResponse.ErrorListener() {
////                    @Override
////                    public void onError(ScalingoError error) {
////
////                    }
////                }
////        );
////
////    }
////    private Class<T> type;
//
////    public GenericDaoImpl(Class<T> type, Scalingo server) {
////        this.type = type;
////    }
////
////    public PK create(T o) {
//////        return (PK) getSession().save(o);
////    }
////
////    public T retrieve(PK id) {
//////        return (T) getSession().get(type, id);
////    }
//}
