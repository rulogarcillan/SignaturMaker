package com.signaturemaker.app.application.features.files

import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.ViewCompat
import co.dift.ui.SwipeToAction
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.SwipeLayout.SwipeListener
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.signaturemaker.app.R
import com.signaturemaker.app.application.core.extensions.Utils
import com.signaturemaker.app.application.core.extensions.loadFromUrl
import com.signaturemaker.app.application.features.image.ImageActivity
import com.signaturemaker.app.data.repositories.SharedPreferencesRepository
import com.signaturemaker.app.databinding.ItemExploreBinding
import com.signaturemaker.app.domain.models.ItemFile
import com.tuppersoft.skizo.core.extension.gone
import com.tuppersoft.skizo.core.extension.visible
import java.util.concurrent.atomic.AtomicBoolean

class FilesViewHolder(private val binding: ItemExploreBinding) : SwipeToAction.ViewHolder<ItemFile>(binding.root) {
    companion object {

        private const val FIRST_HELP = "FIRST_HELP"
    }

    val shareBoolean: AtomicBoolean = AtomicBoolean(false)
    val deleteBoolean: AtomicBoolean = AtomicBoolean(false)
    private val swipe: Animation by lazy { AnimationUtils.loadAnimation(binding.root.context, R.anim.swipe_icon) }

    fun bind(
        item: ItemFile,
        position: Int,
        manager: SwipeItemRecyclerMangerImpl,
        onClickItem: ((item: ItemFile, imageView: ImageView) -> Unit)? = null,
        onClickShare: ((item: ItemFile) -> Unit)? = null,
        onClickDelete: ((item: ItemFile) -> Unit)? = null

    ) {
        handleAnim(position)
        setTransitionIds(item)

        binding.textName.text = item.name.substring(3)
        binding.textDate.text = item.date
        binding.textSize.text = item.size

        binding.imgSign.loadFromUrl("file:///" + Utils.path + "/" + item.name)

        binding.principalLayer.setOnClickListener {
            onClickItem?.invoke(item, binding.imgSign)
        }


        binding.share.setOnClickListener {
            shareBoolean.set(true)
            binding.swipelayout.close()
        }

        binding.delete.setOnClickListener {
            deleteBoolean.set(true)
            binding.swipelayout.close()
        }

        binding.swipelayout.addSwipeListener(object : SwipeListener {
            override fun onStartOpen(layout: SwipeLayout?) {
                manager.closeAllExcept(layout)
            }

            override fun onOpen(layout: SwipeLayout?) {
            }

            override fun onStartClose(layout: SwipeLayout?) {
            }

            override fun onClose(layout: SwipeLayout?) {
                if (shareBoolean.compareAndSet(true, false)) {
                    onClickShare?.invoke(item)
                }
                if (deleteBoolean.compareAndSet(true, false)) {
                    onClickDelete?.invoke(item)
                }
            }

            override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
            }

            override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
            }
        })
    }

    private fun setTransitionIds(item: ItemFile) {
        ViewCompat.setTransitionName(binding.imgSign, item.name + ImageActivity.PHOTO)
    }

    private fun handleAnim(position: Int) {
        if (position == 0 && SharedPreferencesRepository.loadPreference(binding.root.context, FIRST_HELP, true)) {
            binding.ivSwipe.visible()
            SharedPreferencesRepository.savePreference(binding.root.context, FIRST_HELP, false)
            binding.ivSwipe.startAnimation(swipe)
            swipe.setAnimationListener(object : AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    binding.ivSwipe.visibility= View.INVISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
        }
    }
}
