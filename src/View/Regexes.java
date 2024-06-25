package View;

public enum Regexes {
    showMenuName("show menu name"),
    userRegister("user create -u (?<username>[a-zA-Z0-9]+) -p (?<password>(.+)) (?<passConfirm>(.+)) -email (?<email>(.+)) -n (?<nickName>[a-zA-Z0-9]+)"),
    QE("question pick -q (?<QENumber>[a-zA-Z0-9]+) -a (?<answer>[a-zA-Z0-9]+) -c (?<answerConfirm>[a-zA-Z0-9]+)"),
    userLogin("user login -u (?<username>[a-zA-Z0-9]+) -p (?<password>(.+))"),
    addToQueue("add -t (?<track>[a-zA-Z0-9\\s]+) to queue"),
    likeTrack("like track -t (?<track>[a-zA-Z0-9\\s]+)"),
    removeFromQueue("remove -t (?<track>[a-zA-Z0-9\\s]+) from queue"),
    removeFromLikedTracks("remove -t (?<track>[a-zA-Z0-9\\s]+) from liked tracks"),
    reverseOrder("reverse order of queue from (?<start>[0-9-]+) to (?<end>[0-9-]+)"),
    creatPlaylist("create -p (?<playlist>[a-zA-Z0-9\\s]+)"),
    deletePlaylist("delete -p (?<playlist>[a-zA-Z0-9\\s]+)"),
    followUser("follow user -u (?<username>[a-zA-Z0-9]+)"),
    followArtist("follow artist -u (?<username>[a-zA-Z0-9]+)"),
    unfollowUser("unfollow user -u (?<username>[a-zA-Z0-9]+)"),
    unfollowArtist("unfollow artist -u (?<username>[a-zA-Z0-9]+)"),
    toPlaylistMenu("go to playlist menu -p (?<playlist>[a-zA-Z0-9\\s]+)"),
    showTrackInfo("show track info -t (?<track>[a-zA-Z0-9\\s]+)"),
    showPlaylistInfo("show playlist info -p (?<playlist>[a-zA-Z0-9\\s]+)"),
    showArtistInfo("show artist info -u (?<username>[a-zA-Z0-9]+)"),
    showUserInfo("show user info -u (?<username>[a-zA-Z0-9]+)"),
    releaseTrack("release -n (?<track>[a-zA-Z0-9\\s]+) -t (?<type>song|podcast) -d (?<duration>[0-9]+) -r (?<release>[0-9]{4}/[0-9]{2}/[0-9]{2})"),
    addTrackToPlaylist("add -t (?<track>[a-zA-Z0-9\\s]+)"),
    removeTrackFromPlaylist("remove -t (?<track>[a-zA-Z0-9\\s]+)");
    public final String pattern;

    Regexes(String pattern) {
        this.pattern = pattern;
    }
}
