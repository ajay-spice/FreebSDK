package in.freebsdk.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.freeb.sdk.R;
import in.freeb.sdk.model_gson.FetchOffer;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freebsdk.adapters.FreeBOffersAdapter;

/**
* Inflate the layout to display the Offers in List View in your application
*/
public class FreeBOffersFragment extends Fragment {

    private ListView offers_list_view;
    private Context mContext;

    public static List<FetchOffer> freeBOffersList;

    public static List<FetchOffer> freeBOffersListFilter;
    private FreeBOffersAdapter mAdapter;
    private ImageButton cross;
    private String itemId = "";

    TextView emptyTxt;

    @SuppressWarnings("static-access")
    public FreeBOffersFragment(
            List<FetchOffer> freeBOffersListFilter) {

        this.freeBOffersListFilter = freeBOffersListFilter;
    }

    public FreeBOffersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.freeb_fetch_offers, container,
                false);
        mContext = getActivity();
        freeBOffersList = new ArrayList<FetchOffer>();
        offers_list_view = (ListView) view.findViewById(R.id.offers_list_view);
        emptyTxt = (TextView) view.findViewById(R.id.emptyTxt);

        try {

            itemId = getArguments().getString(FreeBConstants.ITEMID);
            new SetFetchOffers().execute();

            // Offer list on click listener
            offers_list_view.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("POSITION", position);
                    bundle.putString(FreeBConstants.ITEMID, itemId);
                    FreeBOffersDetail fm = new FreeBOffersDetail();
                    FragmentTransaction ft = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fm.setArguments(bundle);

                    ft.add(((ViewGroup) (getView().getParent())).getId(), fm);
                    ft.addToBackStack(null);

                    ft.commit();

                }
            });

        } catch (Exception e) {

            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();

                    return true;
                } else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Get a reference to the parent Activity.
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    class SetFetchOffers extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String installedApps = FreeBCommonUtility
                    .getInstalledAppsPackageName(mContext);
            for (int k = 0; k < freeBOffersListFilter.size(); k++) {

                FreeBSDKLogger.d("LIST SIZE", ""
                        + freeBOffersListFilter.get(k).getPackageName());
                if (freeBOffersListFilter.get(k).getCheckInstallApps()
                        .equals("true")) {
                    if (installedApps.contains(freeBOffersListFilter.get(k)
                            .getPackageName())) {

                    } else {
                        freeBOffersList.add(freeBOffersListFilter.get(k));
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {  /**************** Create Custom Adapter *********/
            super.onPostExecute(result);
            try {
                mAdapter = new FreeBOffersAdapter(mContext, freeBOffersList);
                offers_list_view.setAdapter(mAdapter);
                offers_list_view.setEmptyView(emptyTxt);

            } catch (Exception e) {

                // Crashlytics.logException(e);
                FreeBSDKLogger.e(getClass().getName(),
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);
            }

        }

    }

}
