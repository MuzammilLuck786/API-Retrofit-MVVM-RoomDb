import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.apirectrofitmvvmroomdb.databinding.ImageRowLayoutBinding
import com.api.apirectrofitmvvmroomdb.interfaces.AdapterClick
import com.api.apirectrofitmvvmroomdb.models.Hit

class ImageAdapter(private var hits: List<Hit>, private val adapterClick: AdapterClick) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hit = hits[position]

        holder.binding.hit = hit
        holder.binding.adapterClick = adapterClick
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return hits.size
    }

    class ImageViewHolder(val binding: ImageRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
