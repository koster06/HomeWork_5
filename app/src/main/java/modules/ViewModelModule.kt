package modules

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import viewmodels.MyViewModel

@Module
class ViewModelModule {
    @Provides
    fun provideMyViewModel(myViewModel: MyViewModel): ViewModel {
        return myViewModel
    }
}


