package com.deezer.android.cordovadeezerplugin;



public interface DeezerJSListener {
    
    public void init(String appId);
    
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
