package com.deezer.android.cordovadeezerplugin;

import org.json.JSONObject;


public interface DeezerJSListener {
    
    public void onWebViewLoaded(JSONObject json);
    
    public void onPlayTracks(final String ids, int index, int offset,
            boolean autoPlay, boolean addToQueue);
    
    public void onPlayAlbum(final String id, int index, int offset,
            boolean autoPlay, boolean addToQueue);
    
    public void onPlayPlaylist(final String id, int index, int offset,
            boolean autoPlay, boolean addToQueue);
    
    public void onPlayRadio(final String id, int index, int offset,
            boolean autoPlay, boolean addToQueue);
    
    public void onPlayArtistRadio(final String id, int index, int offset,
            boolean autoPlay, boolean addToQueue);
    
    public void onPlay();
    
    public void onPause();
    
    public void onNext();
    
    public void onPrev();
    
}
