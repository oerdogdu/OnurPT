package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.utils.SharedPreferenceHelper;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentValues.TAG;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

/**
 * Created by Atoi on 1.01.2018.
 */

public class MusicActivity extends Activity  implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback
{
    private SpotifyService spotify;
    private String token;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private ArrayList<String> uris = new ArrayList<>();
    private ImageButton playButton, stopButton, shuffleButton,
                repeatButton, forwardButton, backwardButton;
    private FloatingActionButton fabMusicLocal, fabSpotify;
    private Metadata mMetadata;
    private int second;
    private long durationMs;

    private final SpotifyPlayer.OperationCallback mOperationCallback = new SpotifyPlayer.OperationCallback() {
        @Override
        public void onSuccess() {
            mPlayer.getMetadata();
        }

        @Override
        public void onError(Error error) {

        }
    };

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "7f1860310b1c486d8f6c76f27b39e43c";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "OnurPT://callback";

    private SpotifyPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);

        mSharedPreferenceHelper = new SharedPreferenceHelper(MusicActivity.this);

        playButton = (ImageButton)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedIn())
                {
                    mPlayer.playUri(null, "spotify:user:justintilley:playlist:0N3272Mk8VHmWlHsL49WXs",
                            0,0);
                    playButton.setVisibility(View.GONE);
                    stopButton.setVisibility(View.VISIBLE);

                }
            }
        });

        stopButton = (ImageButton)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedIn())
                {
                    mPlayer.pause(null);
                    stopButton.setVisibility(View.GONE);
                    playButton.setVisibility(View.VISIBLE);
                }
            }
        });

        fabSpotify = (FloatingActionButton)findViewById(R.id.fabSpotify);
        fabSpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLoggedIn())
                {
                    AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                            AuthenticationResponse.Type.TOKEN,
                            REDIRECT_URI);
                    builder.setScopes(new String[]{"user-read-private", "streaming"});
                    AuthenticationRequest request = builder.build();

                    AuthenticationClient.openLoginActivity(MusicActivity.this, REQUEST_CODE, request);
                }
                else
                {
                    Toast.makeText(MusicActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shuffleButton = (ImageButton)findViewById(R.id.buttonShuffle);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedIn())
                {
                    Toast.makeText(MusicActivity.this, "Shuffling!", Toast.LENGTH_SHORT).show();
                    mPlayer.setShuffle(null, true);
                }
            }
        });

        repeatButton = (ImageButton)findViewById(R.id.buttonRepeat);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedIn())
                {
                    Toast.makeText(MusicActivity.this, "Repeat", Toast.LENGTH_SHORT).show();
                    mPlayer.setRepeat(null, true);
                }
            }
        });

        forwardButton = (ImageButton)findViewById(R.id.fastForward);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.skipToNext(null);
            }
        });

        backwardButton = (ImageButton)findViewById(R.id.fastBackward);
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.skipToPrevious(null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN)    {
                token = response.getAccessToken();
                mSharedPreferenceHelper.saveSpotifyToken(token);
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MusicActivity.this);
                        mPlayer.addNotificationCallback(MusicActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }

    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        mMetadata = mPlayer.getMetadata();
        durationMs = mMetadata.currentTrack.durationMs;
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    private boolean isLoggedIn() {
        return mPlayer != null && mPlayer.isLoggedIn();
    }

    @Override
    public void onLoggedIn() {
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(mSharedPreferenceHelper.getCurrentSpotifyToken());

        spotify = api.getService();
        spotify.getMe(new Callback<UserPrivate>() {
            @Override
            public void success(UserPrivate userPrivate, Response response) {
                mSharedPreferenceHelper.saveCurrentUserId(userPrivate.id);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "FAILED to Obtain User Information.");
            }
        });

        Log.d("userid", mSharedPreferenceHelper.getCurrentUserId());
        Log.d("token", mSharedPreferenceHelper.getCurrentSpotifyToken());

    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }
}
