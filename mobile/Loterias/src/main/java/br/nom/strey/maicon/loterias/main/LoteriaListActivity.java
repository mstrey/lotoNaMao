package br.nom.strey.maicon.loterias.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.megasena.MegaListFragment;
import br.nom.strey.maicon.loterias.megasena.MegasenaResultadosDAO;
import br.nom.strey.maicon.loterias.quina.QuinaListFragment;
import br.nom.strey.maicon.loterias.quina.QuinaResultadosDAO;


/**
 * An activity representing a list of loterias. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link LoteriaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link LoteriaListFragment} and the item details
 * (if present) is a {@link LoteriaDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link LoteriaListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class LoteriaListActivity extends FragmentActivity
        implements LoteriaListFragment.Callbacks {

    public static final String TWO_PANE = "two_pane";
    private final String TAG = "ListActivity";
    Fragment fragment = new LoteriaDetailFragment();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loteria_list);
        Log.d(TAG, "1onCreate");
        MegasenaResultadosDAO dao_mega = new MegasenaResultadosDAO(getBaseContext());
        dao_mega.getMaxConcRemote();

        QuinaResultadosDAO dao_quina = new QuinaResultadosDAO(getBaseContext());
        dao_quina.getMaxConcRemote();

        if (findViewById(R.id.loteria_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((LoteriaListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.loteria_list))
                    .setActivateOnItemClick(true);

            Fragment fragment = new LoteriaDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loteria_detail_container, fragment)
                    .commit();

        }


        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link LoteriaListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        Log.d(TAG, "onItemSelected");

        if (mTwoPane) {
            // TODO: corrigir erro ao carregar o menu da action bar em tablets (painel duplo)
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Log.d(TAG, "onItemSelected_twoPane");
            Bundle arguments = new Bundle();
            arguments.putString(LoteriaDetailActivity.ARG_ITEM_ID, id);
            arguments.putBoolean(TWO_PANE, mTwoPane);

            switch (Integer.parseInt(id)) {
                case 1:
                    fragment = new LoteriaDetailFragment();
                    break;
                case 2:
                    fragment = new MegaListFragment();
                    break;
                case 3:
                    fragment = new QuinaListFragment();
                    break;
                default:
                    break;

            }

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loteria_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Log.d(TAG, "onItemSelected_singlePane");
            Intent detailIntent = new Intent(this, LoteriaDetailActivity.class);
            detailIntent.putExtra(LoteriaDetailActivity.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

}
