/*
 __ _                   _                                 _
/ _(_) __ _ _ __   __ _| |_ _   _ _ __ ___    /\/\   __ _| | _____ _ __
\ \| |/ _` | '_ \ / _` | __| | | | '__/ _ \  /    \ / _` | |/ / _ \ '__|
_\ \ | (_| | | | | (_| | |_| |_| | | |  __/ / /\/\ \ (_| |   <  __/ |
\__/_|\__, |_| |_|\__,_|\__|\__,_|_|  \___| \/    \/\__,_|_|\_\___|_|
      |___/

Copyright (C) 2018  Raúl Rodríguez Concepción www.wepica.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
package com.signaturemaker.app.activities;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.signaturemaker.app.R;
import com.signaturemaker.app.utils.Constants;
import com.signaturemaker.app.utils.Utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Utils.loadPreference(this, Constants.FIRST_TIME, true)) {
            Utils.savePreference(this, Constants.FIRST_TIME, false);
        } else {
            launchActivityMain();
        }

        Fragment f1 = AppIntroFragment.newInstance("Firma", "Roboto", "Digitalice su firma de una forma fácil y sencilla", "normal",
                R.drawable.ic_pencil_icon, getResources().getColor(R.color.background_sliders), getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite));
        Fragment f2 = AppIntroFragment.newInstance("Transfiere", "Roboto", "Guarde y envíe su firma donde usted quiera", "normal",
                R.drawable.ic_share_icon, getResources().getColor(R.color.background_sliders), getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite));
        Fragment f3 = AppIntroFragment.newInstance("Adjunta", "Roboto", "Adjunte la imagen con la firma en su documento", "normal",
                R.drawable.ic_sign_icon, getResources().getColor(R.color.background_sliders), getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite));
        Fragment f4 = AppIntroFragment.newInstance("Listo", "Roboto", "¿Estás preparado?", "normal",
                R.drawable.ic_check_icon, getResources().getColor(R.color.background_sliders), getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite));
        addSlide(f1);
        addSlide(f2);
        addSlide(f3);
        addSlide(f4);


        showSkipButton(false);
        setProgressButtonEnabled(true);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        //launchActivityMain();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        launchActivityMain();

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


    private void launchActivityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
        finish();
    }
}