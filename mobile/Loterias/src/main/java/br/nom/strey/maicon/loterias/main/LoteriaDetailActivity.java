package br.nom.strey.maicon.loterias.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.megasena.MegaEditFragment;
import br.nom.strey.maicon.loterias.megasena.MegaListFragment;
import br.nom.strey.maicon.loterias.megasena.MegasenaVolantesVO;
import br.nom.strey.maicon.loterias.quina.QuinaEditFragment;
import br.nom.strey.maicon.loterias.quina.QuinaListFragment;
import br.nom.strey.maicon.loterias.quina.QuinaVolantesVO;

public class LoteriaDetailActivity extends FragmentActivity {

    public static final String ARG_ITEM_ID = "item_id";
    private final String TAG = "DetailActivity";
    Fragment fragment = new Fragment();
    private Integer category;
    private boolean mTwoPane;

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loteria_detail);
        Log.d(TAG, "setContentView");

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            category = Integer.parseInt(getIntent().getStringExtra(ARG_ITEM_ID));

            switch (category) {
                case 1:
                    fragment = new LoteriaDetailFragment();
                    break;

                case 2:
                    fragment = new MegaListFragment();
                    break;

                case 3:
                    fragment = new QuinaListFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.loteria_detail_container, fragment)
                    .commit();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, LoteriaListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setNumber(View v) {
        switch (category) {
            case 1:

                break;

            case 2:
                if (MegaEditFragment.class == fragment.getClass()) {
                    ((MegaEditFragment) fragment).setNumber(v);
                }
                break;

            case 3:
                if (QuinaEditFragment.class == fragment.getClass()) {
                    ((QuinaEditFragment) fragment).setNumber(v);
                }
                break;
        }
    }

    public void exibeToast(Integer resource) {
        Toast.makeText(this, resource, Toast.LENGTH_LONG).show();
    }

    public void listMegaFragment() {
        fragment = new MegaListFragment();

        replaceFragment();
    }

    public void listQuinaFragment() {
        fragment = new QuinaListFragment();

        replaceFragment();
    }

    public void editMegaFragment(MegasenaVolantesVO vo) {
        fragment = new MegaEditFragment(vo);

        replaceFragment();
    }

    public void editMegaFragment() {
        fragment = new MegaEditFragment();

        replaceFragment();
    }

    public void editQuinaFragment(QuinaVolantesVO vo) {
        fragment = new QuinaEditFragment(vo);

        replaceFragment();
    }

    public void editQuinaFragment() {
        fragment = new QuinaEditFragment();

        replaceFragment();
    }

    private void replaceFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loteria_detail_container, fragment)
                .setCustomAnimations(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE,
                        FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(null)
                .commit();

    }

    public void refreshFragmentList(Boolean exibe_acertos) {
        if (MegaListFragment.class == fragment.getClass()) {
            ((MegaListFragment) fragment).refreshVolantesList(exibe_acertos);
        } else if (QuinaListFragment.class == fragment.getClass()) {
            ((QuinaListFragment) fragment).refreshVolantesList(exibe_acertos);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loteria_detail_container, fragment)
                .setCustomAnimations(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE,
                        FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();

    }

}
