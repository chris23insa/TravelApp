package com.example.chris.travelorga_kth.base_component;

import java.util.ArrayList;

public interface Callable {

    void operation();

    interface CallableArgTripList {
        void operationArgTrip(ArrayList<Trip> t);
    }

    interface CallableArgTrip{
        void operationArgTrip(Trip t);
    }

    interface CallableArgParticipantList {
        void operationArgParticipant(ArrayList<Participants> t);
    }

    interface CallableArgParticipant {
        void operationArgParticipant(Participants t);
    }

    interface CallableArgActitivyList {
        void operationCallableArgActitivyArrayList(ArrayList<TripActivity> t);
    }
    interface CallableArgActitivy {
        void operationCallableArgActitivy(TripActivity t);
    }



}


