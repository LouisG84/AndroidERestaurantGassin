package fr.isen.gassin.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarouselAdapter(val activity: AppCompatActivity, val images: ArrayList<String>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {

        return PictureFragment.newInstance(images[position])
    }


}
