package org.acme;

import io.aidan.films.Films;
import io.aidan.films.RateRequest;
import io.aidan.films.Rating;
import io.aidan.films.RatingRequest;
import io.quarkus.example.Greeter;
import io.quarkus.example.HelloReply;
import io.quarkus.example.HelloRequest;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/films")
public class RestClient {
    @GrpcClient
    Films films;

    @GET
    @Path("/{filmName}")
    public Uni<Integer> getRating(String filmName) {
        return films.getRating(RatingRequest.newBuilder().setFilmName(filmName).build())
                .onItem().transform(Rating::getRating);
    }

    @PUT
    @Path("/{filmName}/rate/{rating}")
    public Uni<Integer> putRating(String filmName, Integer rating) {
        return films.rate(RateRequest.newBuilder().setFilmName(filmName).setRating(rating).build())
                .replaceWith(rating);
    }
}