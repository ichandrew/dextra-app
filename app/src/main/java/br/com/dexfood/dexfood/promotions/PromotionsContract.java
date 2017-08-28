package br.com.dexfood.dexfood.promotions;

import java.util.List;

import br.com.dexfood.dexfood.data.Promotion;

/**
 * Created by Italo Andrew <italoandrewmaciel@gmail.com> on 26/08/17.
 * MOBBEW - http://www.mobbew.com.br
 */

public interface PromotionsContract {
    interface View {
        void showPromotions(List<Promotion> promotions);
        void setProgressIndicator(boolean active);
        void setEmptyMessage(boolean active);
    }

    interface Presenter {
        void loadPromotions();
    }
}
