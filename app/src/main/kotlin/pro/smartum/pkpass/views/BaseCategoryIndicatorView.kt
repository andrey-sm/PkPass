package pro.smartum.pkpass.views

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.category_indicator.view.*
import pro.smartum.pkpass.R
import pro.smartum.pkpass.function.getCategoryTopImageRes
import pro.smartum.pkpass.reader.PassType

open class BaseCategoryIndicatorView @JvmOverloads constructor(context: Context, attrs: AttributeSet, @LayoutRes val layoutRes: Int = R.layout.category_indicator_base) : LinearLayout(context, attrs) {


    override fun onFinishInflate() {
        super.onFinishInflate()
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }

    fun setImageByCategory(category: PassType?) {
        if (category == null) {
            topImageView.visibility = View.GONE
        } else {
            topImageView.visibility = View.VISIBLE
            topImageView.setImageResource(getCategoryTopImageRes(category))
        }
    }

    fun setAccentColor(color: Int) = setBackgroundColor(color)
}
