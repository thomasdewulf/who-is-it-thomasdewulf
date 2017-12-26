package be.thomasdewulf.whoisit.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.activities.MainActivity;
import be.thomasdewulf.whoisit.databinding.FragmentDetailBinding;
import be.thomasdewulf.whoisit.ui.viewmodel.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment
{


    private FragmentDetailBinding binding;
    private SharedViewModel viewModel;
    private String transitionName;

    public DetailFragment()
    {
        // Required empty public constructor
    }

    public void setTransitionName(String transitionName)
    {
        this.transitionName = transitionName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //viewmodel ophalen
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        binding.setCharacter(viewModel.getSelectedCharacter());
        setupUI();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            binding.backdrop.setTransitionName(transitionName);
        }
        Picasso.with(getContext())
                .load("file:////" + viewModel.getSelectedCharacter().getImageUrl())
                .noFade()
                .into(binding.backdrop, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError()
                    {
                        startPostponedEnterTransition();
                    }
                });


    }

    private void setupUI()
    {
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(binding.toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v ->
        {
            activity.onBackPressed();
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_character_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_edit)
        {
            //TODO: edit

            return true;
        } else if(id == R.id.action_delete)
        {
            //TODO: delete

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
