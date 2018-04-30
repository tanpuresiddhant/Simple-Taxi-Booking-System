package com.taxiBooking.service;

import com.taxiBooking.model.Location;
import com.taxiBooking.model.Taxi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class serviceController {
    Taxi[] cars = null;

    @RequestMapping(path = "/api/reset", method = RequestMethod.PUT)
    public ResponseEntity<String> reset() {
        cars = new Taxi[3];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Taxi(i + 1);
        }

        if (cars == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(path = "/api/check", method = RequestMethod.GET)
    public ResponseEntity<String> check() {
        if (cars == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        StringBuilder buildResponse = new StringBuilder();
        for (Taxi t : cars) {
            buildResponse.append(t.toString());
        }
        return new ResponseEntity(buildResponse.toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/api/book/", method = RequestMethod.PUT)
    public ResponseEntity<String> book(@RequestBody Map<String, Location> booking) {
        if (cars == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Location clientSource = booking.get("source");
        Location clientDestination = booking.get("destination");

        int client_to_destination_distance = getDistance(clientSource, clientDestination);

        int car_to_client_distance = Integer.MAX_VALUE;

        int finalCar = -1;
        for (int i = 0; i < cars.length; i++) {
            if (!cars[i].isBooked()) {
                int dist = getDistance(clientSource, cars[i].getTaxiLocation());
                if (dist < car_to_client_distance) {
                    finalCar = i;
                    car_to_client_distance = dist;
                }
            }
        }

        String res = "";
        if (finalCar != -1) {
            cars[finalCar].setBooked(true);
            cars[finalCar].setTotal_time(getDistance(clientSource, cars[finalCar].getTaxiLocation()) + client_to_destination_distance);
            cars[finalCar].setCutomerLocation(clientSource);
            cars[finalCar].setDestinationLocation(clientDestination);
            res = "{car_id: " + String.valueOf(cars[finalCar].getId()) + ", total_time: " + String.valueOf(cars[finalCar].getTotal_time()) + "}";
            return new ResponseEntity(res, HttpStatus.OK);
        }

        return new ResponseEntity(res, HttpStatus.OK);
    }

    private int getDistance(Location source, Location destination) {
        return Math.abs(source.getX() - destination.getX()) + Math.abs(source.getY() - destination.getY());
    }

    @RequestMapping(path = "/api/tick", method = RequestMethod.PUT)
    public ResponseEntity<String> tick() {
        if (cars == null) return new ResponseEntity(HttpStatus.OK);

        for (int i = 0; i < cars.length; i++) {
            if (cars[i].isBooked()) {
                cars[i].setTotal_time(cars[i].getTotal_time() - 1);
                if (cars[i].getTotal_time() == 0) {
                    cars[i].setBooked(false);
                    cars[i].setTaxiLocation(cars[i].getDestinationLocation());
                }
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}

