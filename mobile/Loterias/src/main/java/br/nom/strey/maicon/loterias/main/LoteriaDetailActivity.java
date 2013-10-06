package br.nom.strey.maicon.loterias.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.megasena.MegaEditFragment;
import br.nom.strey.maicon.loterias.megasena.MegaListFragment;
import br.nom.strey.maicon.loterias.megasena.MegasenaVolantesVO;
import br.nom.strey.maicon.loterias.quina.QuinaDetailFragment;

public class LoteriaDetailActivity extends FragmentActivity {

    public static final String ARG_ITEM_ID = "item_id";
    private final String TAG = "DetailActivity";
    MegaEditFragment megaEditFragment = new MegaEditFragment();
    MegaListFragment megaListFragment = new MegaListFragment();
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
                    LoteriaDetailFragment principalFragment = new LoteriaDetailFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.loteria_detail_container, principalFragment)
                            .commit();

                    break;

                case 2:

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.loteria_detail_container, megaListFragment)
                            .commit();

                    break;

                case 3:
                    QuinaDetailFragment quinaFragment = new QuinaDetailFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.loteria_detail_container, quinaFragment)
                            .commit();

                    break;
            }

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
                megaEditFragment.setNumber(v);
                break;

            case 3:
                break;
        }
    }

    public void setListFooter(View v) {

    }

    public void exibeToast(Integer resource) {
        Toast.makeText(this, resource, Toast.LENGTH_LONG).show();
    }

    public void editMegaFragment(MegasenaVolantesVO vo) {
        megaEditFragment = new MegaEditFragment(vo);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loteria_detail_container, megaEditFragment)
                .commit();
    }

    public void editMegaFragment() {
        megaEditFragment = new MegaEditFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loteria_detail_container, megaEditFragment)
                .commit();
    }


}
