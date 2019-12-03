//package com.example.chris.travelorga_kth.network;
//
//import org.json.JSONObject;
//
//import java.io.Serializable;
//
//public class GenericDaoImpl <T extends ScalingoModel, PK extends Serializable> implements GenericDao<T, PK> {
//    private Scalingo server;
//
//    public GenericDaoImpl(Scalingo server) {
//        this.server = server;
//    }
//
//    public void retrieve(PK id) {
//        JSONObject requestBody = new JSONObject();
//
//        server.sendRequest(
//                ScalingoMethod.GET,
//                endpoints.get
//        )
//        return (T) getSession().get(type, id);
//    }
//
//    public PK create(T entity) {
//        JSONObject requestBody = entity.jsonify();
//
//        server.sendRequest(
//                ScalingoMethod.POST,
//                entity.getCreateEndpoint(),
//                requestBody,
//                new ScalingoResponse.SuccessListener<T>() {
//                    @Override
//                    public void onSuccess(entity) {
//
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//
//                    }
//                }
//        );
//
//    }
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
