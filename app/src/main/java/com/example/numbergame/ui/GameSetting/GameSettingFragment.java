package com.example.numbergame.ui.GameSetting;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentGamesettingBinding;
import com.example.numbergame.login.LoginViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class GameSettingFragment extends Fragment
{
    SharedPreferences sharedPreferences;
    String locale;
    int locale_number;
    ArrayList<String> locales;
    ArrayAdapter adapter;
    private FragmentGamesettingBinding binding;
    private GameSettingViewModel gameSettingViewModel;
    private Spinner sp_language;
    private TextView tv_maxNumber;
    private EditText et_maxNumber;
    private Button bt_gameStart;

    private  long pressedTime = 0;

    public GameSettingFragment()
    {
    }

    public static GameSettingFragment newInstance()
    {
        GameSettingFragment fragment = new GameSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public static String getStringByLocal(Activity context, int resId, String locale)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getStringByLocalPlus17(context, resId, locale);
        else
            return getStringByLocalBefore17(context, resId, locale);
    }

    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static String getStringByLocalPlus17(Activity context, int resId, String locale)
    {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(resId);
    }

    private static String getStringByLocalBefore17(Context context, int resId, String language)
    {
        Resources currentResources = context.getResources();
        AssetManager assets = currentResources.getAssets();
        DisplayMetrics metrics = currentResources.getDisplayMetrics();
        Configuration config = new Configuration(currentResources.getConfiguration());
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        config.locale = locale;

        Resources defaultLocaleResources = new Resources(assets, metrics, config);
        String string = defaultLocaleResources.getString(resId);
        new Resources(assets, metrics, currentResources.getConfiguration());
        return string;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameSettingViewModel = new ViewModelProvider(requireActivity()).get(GameSettingViewModel.class);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(System.currentTimeMillis()>pressedTime+2000){
                    pressedTime = System.currentTimeMillis();
                    Toast.makeText(requireContext(),"Press once more to exit",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireContext(),"Exit the app",Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        binding = FragmentGamesettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState)
    {
        super.onViewCreated(view, saveInstanceState);
        sp_language = binding.gameSettingSpLanguage;
        tv_maxNumber = binding.gameSettingTvMaxNumberLabel;
        et_maxNumber = binding.gameSettingEtMaxNumber;
        bt_gameStart = binding.gameSettingBtGameStart;

        sharedPreferences = requireActivity().getSharedPreferences("shared", MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            locale = sharedPreferences.getString("locale", getResources().getConfiguration().getLocales().get(0).getLanguage());
        }
        else
        {
            locale = sharedPreferences.getString("locale", Resources.getSystem().getConfiguration().locale.getLanguage());
        }

        switch (locale)
        {
            case "ko":
            {
                locale_number = 0;
                break;
            }
            case "en":
            {
                locale_number = 1;
                break;
            }
        }
        tv_maxNumber.setText(getStringByLocal(requireActivity(), R.string.tv_maxNumber, locale));

        locales = new ArrayList<>();

        locales.add(getStringByLocal(requireActivity(), R.string.korean, locale));
        locales.add(getStringByLocal(requireActivity(), R.string.english, locale));

        adapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, locales);

        sp_language.setAdapter(adapter);
        sp_language.setSelection(locale_number);

        sp_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                if (position != locale_number)
                {
                    switch (position)
                    {
                        case 0:
                        {
                            locale = "ko";
                            break;
                        }
                        case 1:
                        {
                            locale = "en";
                            break;
                        }
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("locale", locale);
                    editor.commit();
                    Intent intent = requireActivity().getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(requireActivity().getBaseContext().getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    requireActivity().finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                sp_language.setSelection(locale_number);
            }
        });

        et_maxNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                gameSettingViewModel.onMaxNumberTextChanged(editable.toString());
            }
        });

        gameSettingViewModel.getGameSettingFormState().observe(getViewLifecycleOwner(), new Observer<GameSettingFormState>()
        {
            @Override
            public void onChanged(GameSettingFormState gameSettingFormState)
            {
                if (gameSettingFormState.getMaxNumberErrorMessage() != null)
                {
                    et_maxNumber.setError(gameSettingFormState.getMaxNumberErrorMessage());
                }

                gameSettingViewModel.setMaxNumberText(et_maxNumber.getText().toString());
                bt_gameStart.setEnabled(gameSettingFormState.isFieldsValid());
            }
        });

        bt_gameStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameSettingFragmentDirections.ActionNavigationGameSettingToGameFragment action = GameSettingFragmentDirections.actionNavigationGameSettingToGameFragment();
                action.setMaxNumber(gameSettingViewModel.getMaxNumber());
                NavHostFragment.findNavController(GameSettingFragment.this).navigate(action);
            }
        });

    }

}