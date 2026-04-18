package com.mycompany.musiccoursework;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named("artistBean")
@SessionScoped
public class ArtistBean implements Serializable
{
    private String search = "";
    private List<Artist> artists;

    public ArtistBean()
    {
        artists = new ArrayList<>();

        artists.add(new Artist("Drake", "Hip Hop"));
        artists.add(new Artist("Taylor Swift", "Pop"));
        artists.add(new Artist("Burna Boy", "Afrobeats"));
        artists.add(new Artist("Central Cee", "UK Rap"));
        artists.add(new Artist("Adele", "Soul"));
        artists.add(new Artist("The Weeknd", "R&B"));
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    public List<Artist> getFilteredArtists()
    {
        if (search == null || search.trim().isEmpty())
        {
            return artists;
        }

        String lowerSearch = search.toLowerCase();

        return artists.stream()
                .filter(artist ->
                        artist.getName().toLowerCase().contains(lowerSearch) ||
                        artist.getGenre().toLowerCase().contains(lowerSearch))
                .collect(Collectors.toList());
    }
}
