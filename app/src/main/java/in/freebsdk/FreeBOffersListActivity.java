package in.freebsdk;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.freeb.sdk.R;
import in.freeb.sdk.model_gson.FetchOffer;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreebSDKApplication;
import in.freebsdk.fragments.FreeBOffersFragment;

/**
 * This will initialize the fragments in Activity,
 * When our activity is running, we can add or replace fragments. For this reason we use FragmentTransaction and
 * we should make a commit after a change, in order to be effective.
 */

public class FreeBOffersListActivity extends FragmentActivity {
    private List<FetchOffer> getOffersList;
    private ImageButton cross;
    private String title, textcolor, layoutcolor, itemId;
    private TextView offerTitle;
    public static Activity instance;
    private RelativeLayout titleLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.freeb_activity_offers);
        instance = this;
        cross = (ImageButton) findViewById(R.id.cross);
        offerTitle = (TextView) findViewById(R.id.offerTitle);
        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        getOffersList = getIntent().getParcelableArrayListExtra(
                FreeBConstants.OFFERS);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        // set title of the screen according to the requirement
        title = prefs.getString(
                FreeBConstants.TITLE, "");

        //set text color of the title given
        textcolor = prefs.getString(
                FreeBConstants.TEXTCOLOR, "");

        // set background color of title layout
        layoutcolor = prefs.getString(
                FreeBConstants.LAYOUTCOLOR, "");
        // each Offer has unique offer id, but after selected it will be consider as itemId for the consumed offer.
        itemId = prefs.getString(
                FreeBConstants.ITEMID, "");

        if (title != null && !title.equals(""))
            offerTitle.setText(Html.fromHtml(title));

        if (textcolor != null && !textcolor.equals(""))
            offerTitle.setTextColor(Color.parseColor(textcolor));
        if (layoutcolor != null && !layoutcolor.equals(""))
            titleLayout.setBackgroundColor(Color.parseColor(layoutcolor));

        if (getOffersList != null && getOffersList.size() != 0) {
            // Inflate offer list fragment to activity
            Bundle bundle = new Bundle();
            bundle.putString(FreeBConstants.ITEMID, itemId);
            FreeBOffersFragment fragment = new FreeBOffersFragment(
                    getOffersList);
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            fragment.setArguments(bundle);
            ft.add(R.id.your_placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        // To close the sdk
        cross.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (FreebSDKApplication.freeBOffersListener != null)
                    FreebSDKApplication.freeBOffersListener
                            .onLeaveApplication(FreeBConstants.sdkClose,
                                    FreeBConstants.sdkCloseMessage);
                finish();
                // The Condition is written to avoid the creation of more than one instances for  FreeB Offers Class
                FreeBOffers.isShown = 0;
            }
        });

    }

    @Override
    public void onBackPressed() {
        //
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (FreebSDKApplication.freeBOffersListener != null)
                FreebSDKApplication.freeBOffersListener
                        .onLeaveApplication(FreeBConstants.sdkClose,
                                FreeBConstants.sdkCloseMessage);
            this.finish();
            // The Condition is written to avoid the creation of duplicate instances for  FreeB Offers Class
            FreeBOffers.isShown = 0;
        } else {

            getSupportFragmentManager().popBackStack();
        }
    }

}
