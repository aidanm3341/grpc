package org.acme;

import io.aidan.films.Films;
import io.aidan.films.RateRequest;
import io.aidan.films.Rating;
import io.aidan.films.RatingRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

import java.util.HashMap;
import java.util.Map;

@GrpcService
public class GrpcFilmsService implements Films {

    private final Map<String, Rating> ratings;

    public GrpcFilmsService() {
        ratings = new HashMap<>();
        ratings.put("whiplash", createRating(5));
        ratings.put("interstellar", createRating(5));
    }

    private Rating createRating(int rating) {
        return Rating.newBuilder().setRating(rating).build();
    }

    @Override
    public Uni<Rating> getRating(RatingRequest request) {
        return Uni.createFrom().item(() -> ratings.get(request.getFilmName().toLowerCase()));
    }

    @Override
    public Uni<Rating> rate(RateRequest request) {
        return Uni.createFrom().item(() ->
                ratings.put(
                        request.getFilmName().toLowerCase(),
                        createRating(request.getRating())
                )
        );
    }
}
