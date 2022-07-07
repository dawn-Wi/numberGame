package com.example.numbergame.ui.GameSetting;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentGamesettingBinding;

import java.util.ArrayList;
import java.util.Locale;

public class GameSettingFragment extends Fragment {
    private FragmentGamesettingBinding binding;
    private GameSettingViewModel gameSettingViewModel;

    private Spinner sp_language;
    private TextView tv_minNumber;
    private EditText et_minNumber;
    private TextView tv_maxNumber;
    private EditText et_maxNumber;
    private Button bt_gameStart;

    SharedPreferences sharedPreferences;
    String locale;
    int locale_number;
    ArrayList<String> locales;
    ArrayAdapter adapter;

    public GameSettingFragment(){}

    public static GameSettingFragment newInstance(){
        GameSettingFragment fragment = new GameSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameSettingViewModel = new ViewModelProvider(requireActivity()).get(GameSettingViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGamesettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        sp_language = binding.gameSettingSpLanguage;
        tv_minNumber = binding.gameSettingTvMinNumber;
        et_minNumber = binding.gameSettingEtMinNumber;
        tv_maxNumber = binding.gameSettingTvMaxNumber;
        et_maxNumber = binding.gameSettingEtMaxNumber;
        bt_gameStart = binding.gameSettingBtGameStart;

        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale = sharedPreferences.getString("locale", getResources().getConfiguration().getLocales().get(0).getLanguage());
        }else{
            locale = sharedPreferences.getString("locale", Resources.getSystem().getConfiguration().locale.getLanguage());
        }

        switch (locale){
            case "ko":{
                locale_number=0;
                break;
            }
            case "en":{
                locale_number=1;
                break;
            }
        }
        tv_minNumber.setText(getStringByLocal(this,R.string.tv_minNumber.locale));

        locales = new ArrayList<>();

        locales.add(getStringByLocal(requireActivity(), R.string.korean,locale));
        locales.add(getStringByLocal(requireActivity(),R.string.english,locale));



    }

    @NonNull
    public static String getStringByLocal(Activity context, int resId, String locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getStringByLocalPlus17(context, resId, locale);
        else
            return getStringByLocalBefore17(context, resId, locale);
    }

   @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static String getStringByLocalPlus17(Activity context, int resId, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(resId);
    }

    private static String getStringByLocalBefore17(Context context, int resId, String language){
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}