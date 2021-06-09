package br.com.tratto.Utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import br.com.tratto.Model.Currency;
import br.com.tratto.R;



public class CurrencyUtil {

    @DrawableRes
    public static int getFlag(@Nullable Currency currency) {
        if (currency == null) return R.drawable.ic_flag_brazil_round;
        switch (currency.currencyCode) {
            case "USD":
                return R.drawable.ic_flag_us;
            case "BRL":
                return R.drawable.ic_flag_brazil_round;
            case "EUR":
                return R.drawable.ic_eu_flag;
            case "BTC":
                return R.drawable.ic_bitcoin;
        }
        return R.drawable.ic_flag_brazil_round;
    }
}
