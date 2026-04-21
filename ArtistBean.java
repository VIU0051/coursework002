/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package javapages;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import entities.Artists;
import entities.Albums;
import entities.Reviews;
import entities.Songs;

@Named("artistBean")
@ViewScoped
public class ArtistBean implements Serializable
{
    @PersistenceContext
    private EntityManager artistTable;
    private Artists selectedArtist;
    private Songs artistSong;
    private Songs selectedSong;
    private Reviews newReview = new Reviews();
    private Albums selectedAlbum = new Albums();
    private String search = "";
    
    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    public Artists getSelectedArtist() {
        return selectedArtist;
    }
    
    public void setSelectedArtist(Artists selectedArtist) {
        this.selectedArtist = selectedArtist;
    }
    
    public void selectArtist(Artists artist) {
        this.selectedArtist = artist;
        this.search = artist.getArtistName();
        System.out.println("Selected: " + artist.getArtistName());
    }
    
    public Albums getSelectedAlbum() {
        return selectedAlbum;
    }
    
    public void setSelectedAlbum(Albums selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }
    
    public List<Songs> getArtistSongs() {
        if(selectedArtist == null) {
            return new ArrayList<>();
        }
        
        return artistTable.createQuery(
            "SELECT s FROM Songs s WHERE s.albumId.artistId = :artist", Songs.class)
                .setParameter("artist", selectedArtist)
                .getResultList();
                
    }
    
    public void selectSong(Songs song) {
        this.artistSong = song;
        System.out.println("CLICKED: " + song.getSongTitle());
    }
    
    public Songs getSelectedSong() {
        return selectedSong;
    }   

    public void setSelectedSong(Songs selectedSong) {
        this.selectedSong = selectedSong;
    }
    
    public Reviews getNewReview() {
        return newReview;
    }
    
    public void addReview() {
        newReview.setSongId(artistSong);
    }
    
    public List<Artists> getFilteredArtists()
    {
        if (search == null || search.trim().isEmpty())
        {
            return artistTable.createQuery("SELECT a FROM Artists a", Artists.class)
                 .getResultList();
        }

        return artistTable.createQuery(
            "SELECT a FROM Artists a WHERE LOWER(a.artistName) LIKE :search OR LOWER(a.genre) LIKE :search",
            Artists.class)
            .setParameter("search", "%" + search.toLowerCase() + "%")
            .getResultList();
    }
    

    public void clearSelection() {
    selectedArtist = null;
    }
}
