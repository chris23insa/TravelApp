package com.example.chris.travelorga_kth.base_component;

import java.util.ArrayList;

public interface Callable {

    void operation();

    interface CallableArgTrip {
        void operationArgTrip(ArrayList<Trip> t);
    }

    interface CallableArgParticipant {
        void operationArgParticipant(ArrayList<Participants> t);
    }

    interface CallableArgActitivy {
        void operationCallableArgActitivyArrayList(ArrayList<TripActivity> t);
    }



}


