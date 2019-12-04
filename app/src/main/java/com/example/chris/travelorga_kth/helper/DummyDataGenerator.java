package com.example.chris.travelorga_kth.helper;

import android.app.Activity;

import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Preference;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DummyDataGenerator {

    public Participants Macron;
    private Participants Jinping;
    private Participants Trump;
    private Participants Merkel;
    private Participants Poutine;
    private Participants Jong_un;
    private Participants Johnson;

    private TripActivity madridCityTour;
    private TripActivity madridPlazaMayor;
    private TripActivity hamburgMuseum;
    private TripActivity londonTower;
    private TripActivity eifelTower;
    private TripActivity tekniskaMuseet;
    private TripActivity statueOfLiberty;

    private final Activity context;
    private final ArrayList<Trip> friendsTrip;
    private final ArrayList<Trip> myTrip;
    private final ArrayList<Trip> allTrip;
    private final ArrayList<TripActivity> activities;



    public DummyDataGenerator(Activity act){
        activities = new ArrayList<>();
        myTrip = new ArrayList<>();
        friendsTrip = new ArrayList<>();
        allTrip = new ArrayList<>();
        context = act;
        generateParticipants();
        generateActivity();

    }

    private void generateParticipants() {
        Macron = new Participants("Emmmanuel", "Macron", "emmanuel",
                "Si on traverse la rue, on en trouve un, d'emploi !",
                null, context);
        Jinping = new Participants("Xi", "Jinping", "xi", "Make China great again", null, context);
        Trump = new Participants("Donald", "Trump", "donald", "Make America great again", null, context);
        Merkel = new Participants("Angela", "Merkel", "angela", "Ich mag Kartoffelsalat", null, context);
        Poutine = new Participants("Vladimir", "Poutine", "vladimir", "I am a nature lover. I try to travel every two months to search for the best landscape in different countries and cities. I come from Russia and i often travel on my own with my backpack on.",
                null, context);
        Jong_un = new Participants("Kim", "Jong-un", "kim", "I am the unique leader of the glorious North Popular Democratic Republic of Korea",
                null, context);
        Johnson = new Participants("Boris", "Johnson", "boris", "Make UK great again", null, context);

        Macron.addFriend(Jinping);
        Macron.addFriend(Merkel);
        Jinping.addFriend(Trump);
        Jinping.addFriend(Poutine);
        Merkel.addFriend(Macron);
        Merkel.addFriend(Jong_un);
        Poutine.addFriend(Trump);
        Poutine.addFriend(Jinping);
        Jong_un.addFriend(Jinping);
        Johnson.addFriend(Trump);
        Johnson.addFriend(Merkel);
        Johnson.addFriend(Jinping);
        Johnson.addFriend(Jong_un);

    }

    private void generateActivity() {
        madridCityTour = new TripActivity(
                "Madrid",
                "City tour",
                "Calle de Felipe IV, s/n, 28014 Madrid, Spain",
                "madridcitytour",
                "11am",
                "12am",
                "City tour of madrid",
                "Discover the city at your own pace in one day and create your own tour of Madrid with the hop-on hop-off service offered by the Madrid City Tour. Our tour buses are double-deckers, with an open-top deck to offer you a marvellous panoramic view of the city.",
                new ArrayList<>(Arrays.asList("Create your own tour", "Open buses", "Panoramic view of the city")),
                new ArrayList<>(Collections.singletonList("Buses every 10min in our bus stop (please check on our website)")),
                new ArrayList<>(Arrays.asList("Adult (More than 16 years old) : 20€",
                        "Junior (Between 7 and 16 years old)  : 10€",
                        "Child (Less than 7 years old) : Free")),
                context);

        londonTower = new TripActivity(
                "London",
                "London Tower",
                " St Katharine's & Wapping, London EC3N 4AB, United Kingdom",
                "londontower",
                "11am",
                "12am",
                "Visit of the london tower",
                "Explore 1000 years of history at London’s iconic castle and World Heritage Site. See the Crown Jewels, take a legendary Yeoman Warder tour and meet the ravens.",
                new ArrayList<>(Collections.singletonList("")),
                new ArrayList<>(Arrays.asList(
                        "Tuesday-Saturday: 09:00-16:30",
                        "Sunday-Monday: 10:00-16:30")),
                new ArrayList<>(Arrays.asList("Adult age 18-64:£24.70",
                        "Age 65+ or 16-17, full-time student, disabled visitor:£19.30",
                        "Child age 5-15:£11.70",
                        "1 adult & up to 3 children (aged 5-15):£44.40",
                        "2 adults & up to 3 children (aged 5-15):£62.90")),
                context);

        eifelTower = new TripActivity(
                "Paris",
                "eifel Tower",
                " Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France",
                "eifeltower",
                "11am",
                "12am",
                "Visit of the eifel tower",
                "Come and discover the Eiffel Tower on the only trip to the top " +
                        "of its kind in Europe, and let pure emotions carry you from the esplanade to the top.",
                new ArrayList<>(Collections.singletonList("")),
                new ArrayList<>(Arrays.asList(
                        "Everyday by lift : 9:30 - 23:45",
                        "Everyday by  stair:9:30 - 18:30")),
                new ArrayList<>(Arrays.asList("Adult age 18-64:£24.70",
                        "Age 65+ or 16-17, full-time student, disabled visitor:£19.30",
                        "Child age 5-15:£11.70",
                        "1 adult & up to 3 children (aged 5-15):£44.40",
                        "2 adults & up to 3 children (aged 5-15):£62.90")),
                context);

        tekniskaMuseet = new TripActivity(
                "Stockholm",
                "tekniska museet",
                "Museivägen 7, 115 27 Stockholm",
                "tekniska",
                "11am",
                "12am",
                "Visit of the teknikska museum",
                "The Swedish National Museum of Science and Technology is a Swedish museum in Stockholm. It is Sweden’s largest museum of technology, and has a national charter to be responsible for preserving the Swedish cultural heritage related to technological and industrial history.",
                new ArrayList<>(Collections.singletonList("Museum of technology")),
                new ArrayList<>(Arrays.asList(
                        "Wednesday	10am–7pm",
                        "Thursday	10am–5pm",
                        "Friday	10am–5pm",
                        "Saturday	10am–5pm",
                        "Sunday	10am–5pm",
                        "Monday	10am–5pm",
                        "Tuesday	10am–5pm")),
                new ArrayList<>(Arrays.asList("More than 7 years old : 15€",
                        "Less than 7  years old: free€")),
                context);

        hamburgMuseum = new TripActivity(
                "Hamburg",
                "Hamburg Museum",
                "Holstenwall 24, 20355 Hamburg, Germany",
                "hamburgmuseum",
                "3m",
                "4pm",
                "Visit of the Museum of Hamburg",
                "The Hamburg Museum, also known as Museum für Hamburgische Geschichte ('Museum for Hamburg History'), is a history museum located in the city of Hamburg in northern Germany. The museum was established at its current location in 1922, although its parent organization was started in 1839. The museum was named hamburgmuseum in 2006. It is located near the Planten un Blomen park in the center of Hamburg.[1] The museum is commonly reviewed among the museums of the city of Hamburg",
                new ArrayList<>(Collections.singletonList("History museum")),
                new ArrayList<>(Arrays.asList("Sunday	10am–6pm",
                        "Monday	10am–5pm",
                        "Tuesday	Closed",
                        "Wednesday	10am–5pm",
                        "Thursday	10am–5pm",
                        "Friday	10am–5pm",
                        "Saturday	10am–6pm")),
                new ArrayList<>(Arrays.asList("Adult (More than 18 years old): 10€", "Other : 5€")),
                context);

        madridPlazaMayor = new TripActivity(
                "Madrid",
                "Madrid Plaza Mayor",
                " Plaza Mayor, 28012 Madrid, Spain",
                "madridplazamayor",
                "10am",
                "11am",
                "Visit of the Plaza Mayor",
                "The Plaza Mayor (English: Main Square) is a major public space in the " +
                        "heart of Madrid, the capital of Spain. It was once the centre of Old Madrid." +
                        " It was first built (1580–1619) during the Habsburg period of Philip III's reign. " +
                        "Only a few Spanish blocks away is another famous plaza, the Puerta del Sol. " +
                        "The Plaza Mayor is for the people of Madrid and tourists to shop, walk around, eat, and enjoy the outdoors. ",
                new ArrayList<>(Arrays.asList("Heart of madrid", "Near to Puerta del Sol")),
                new ArrayList<>(Collections.singletonList("Always open")),
                new ArrayList<>(Collections.singletonList("Free")),
                context);

        statueOfLiberty = new TripActivity(
                "New-york",
                "statueofliberty",
                "Statue of Liberty New York, NY 10004, United States",
                "statueoOfLiberty",
                "10am",
                "2pm",
                "Statue of Liberty & Ellis Island Tour",
                "Check two New York landmarks off your bucket list with this exciting guided tour of the Statue of Liberty and Ellis Island. Start the tour by getting early Reserve Line Access boarding on the ferry to Liberty Island. Once there, go inside the pedestal of the statue and head to the observation decks for an awesome view of New York City and its surroundings. Then hop back on the ferry to Ellis Island to learn about the intriguing history of immigration to America that took place here between 1892 and 1954.",
                new ArrayList<>(Arrays.asList("Guided visit to the Statue of Liberty National Monument and Ellis Island",
                        "Explore the grounds at Liberty Island before heading inside the pedestal for observation decks and a museum",
                        "View exhibits at the Great Hall inside the Immigration Museum on Ellis Island",
                        "Early Reserve Line boarding on the ferry to Liberty Island and Ellis Island")),
                new ArrayList<>(Collections.singletonList("")),
                new ArrayList<>(Collections.singletonList("Group ticket : 56")),
                context);

        activities.add(londonTower);
        activities.add(madridCityTour);
        activities.add(madridPlazaMayor);
        activities.add(hamburgMuseum);
        activities.add(tekniskaMuseet);
        activities.add(eifelTower);
    }
    public ArrayList<TripActivity> getActivities(){
        return activities;
    }
    public  ArrayList<Trip> getMyTrip() {
        myTrip.addAll(Arrays.asList(
                new Trip("Londres",  context.getResources().getIdentifier("londres", "drawable", context.getPackageName()), "21/11/2015", "22/11/2019",
                        "Trip in Londres for 3 days with the best !",
                        new ArrayList<>(Collections.singletonList(londonTower)), new ArrayList<>(Arrays.asList(Trump, Merkel)), 1000,Preference.MUSEUM, context),
                new Trip("Paris", context.getResources().getIdentifier("tour_eiffel", "drawable", context.getPackageName()), "20/29/2017", "21/11/2019",
                        "Trip in Paris to see the eiffel tower, unbelievable !",
                        new ArrayList<>(Collections.singletonList(eifelTower)), new ArrayList<>(Arrays.asList(Poutine, Jinping)),1000,Preference.MUSEUM,  context),
                new Trip("New Yord", context.getResources().getIdentifier("new_york", "drawable", context.getPackageName()), "02/03/2019", "10/03/2019",
                        "New-yok, city of light with my partner in crime.",
                        new ArrayList<>(Collections.singletonList(statueOfLiberty)), new ArrayList<>(Arrays.asList(Jong_un, Johnson)), 1000,Preference.MUSEUM, context),
                new Trip("Stockholm", context.getResources().getIdentifier("stockholm", "drawable", context.getPackageName()), "30/04/2019", "05/05/2019",
                        "Lake, Park, Cold, description of our journey.", new ArrayList<>(Collections.singletonList(tekniskaMuseet)),
                        new ArrayList<>(Arrays.asList(Macron, Merkel, Trump)),1000,Preference.MUSEUM, context)
        ));
        allTrip.addAll(myTrip);
        return  myTrip;
    }

    public  ArrayList<Trip> getFriendsTrip() {
        friendsTrip.addAll(Arrays.asList(
                new Trip("Madrid", context.getResources().getIdentifier("madrid", "drawable", context.getPackageName()), "11/04/2019", "20/04/2019",
                        "Trip in Madrid to discover the tortillas and corrida. !",
                        new ArrayList<>(Arrays.asList(madridCityTour, madridPlazaMayor)),
                        new ArrayList<>(Arrays.asList(Merkel, Macron)),
                        1000,Preference.MUSEUM, context),
                new Trip("Hamburg", context.getResources().getIdentifier("hamburg", "drawable", context.getPackageName()), "17/10/2018", "20/10/2019", "Trip in Hamburg, Amazing !",
                        new ArrayList<>(Collections.singletonList(hamburgMuseum)),
                        new ArrayList<>(Arrays.asList(Johnson, Jinping, Jong_un, Merkel)),
                        1000,Preference.MUSEUM, context)));
        allTrip.addAll(friendsTrip);
        return  friendsTrip;
    }

    public ArrayList<Trip> getAllTrips(){
        return  allTrip;
    }
}
