# WorkerManager

## More information

---

- **Offline Caching** means that your app saves data fetched from the network on the device's local storage, for faster access.
- Many users have intermittent access to the internet. By implementing offline caching, you add offline support to your app, helping these users to use your app while they are offline.
- if app is online-only, the user needs a network connection to use it. in this codelab, you implement offline caching to display results from the local database, instead of from the network.

## The repository pattern

---

- A repository mediates between data sources (such as persistent models, web services, and caches) and the rest of the app.
- Using a repository design pattern is a recommended best practice for code separation and architecture.
- A repository module handles data operations and allows you to use multiple backends.
- the repository implements the logic for deciding whether to fetch data from a network or use results that are cached in a local database.

![WorkerManager%20cd4a543530ce42db92a9eb92e436a5d5/Untitled.png](WorkerManager%20cd4a543530ce42db92a9eb92e436a5d5/Untitled.png)

## **WorkerManager**

---

- WorkManager is for background work that's deferrable and requires guaranteed execution.
- Deferrable means that the work is not required to run immediately.
- Guaranteed execution means that the task will run even if the app exits or the device restarts.
- While WorkManager runs background work, it takes care of compatibility issues and best practices for battery and system health.
- WorkManager offers compatibility back to API level 14. WorkManager chooses an appropriate way to schedule a background task, depending on the device API level. It might use JobScheduler (on API 23 and higher) or a combination of AlarmManager and BroadcastReceiver.

## The following classes in WorkManager library

---

1. **Worker**
- This class is where you define the actual work (the task) to run in the background. You extend this class and override the doWork() method. The doWork() method is where you put code to be performed in the background, such as syncing data with the server or processing images. You implement the Worker in this task

```
class RefreshDataWorker(appContext: Context,params:WorkerParameters)
    :CoroutineWorker(appContext,params) {

    companion object {
        const val WORK_NAME = "com.example.android.devbyteviewer.worker
														.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {

        try {
            MarsApi.retrofitService.getProperties(
															OverviewViewModel.MarsApiFilter.SHOW_ALL.value)

					  Timber.d("Work request for sync is run")

        }catch (e:Exception){
            return Result.retry()
        }
        return Result.success()
    }
}
```

- WORK_NAME constant is to use to add a uniquely named PeriodicWorkRequest to the queue, where only one PeriodicWorkRequest of a particular name can be active at a time.
- The doWork() method inside the Worker class is called on a background thread.
- The method performs work synchronously, and should return a ListenableWorker.Result object. After this time has expired, the system forcefully stops the Worker.
- Result.success()—work completed successfully.
- Result.failure()—work completed with a permanent failure.
- Result.retry()—work encountered a transient failure and should be retried.

2. **WorkRequest**

- This class represents a request to run the worker in background. Use WorkRequest to configure how and when to run the worker task, with the help of Constraints such as device plugged in or Wi-Fi connected. You implement the WorkRequest in a later task.
- The OneTimeWorkRequest class is for one-off tasks. (A one-off task happens only once.)
- The PeriodicWorkRequest class is for periodic work, work that repeats at intervals.
- The minimum interval for periodic work is 15 minutes. Periodic work can't have an initial delay as one of its constraints.
- The supported constraints are the set methods in Constraints.Builder.

```
class DevByteApplication:Application() {

    private fun setupRecurringWork(){

			  val constraints =Constraints.Builder()
						            .setRequiredNetworkType(NetworkType.UNMETERED)
						            .setRequiresBatteryNotLow(true)
						            .setRequiresCharging(true)
						            .apply{
														if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
								                    setRequiresDeviceIdle(true)
								                }
														}
												.build()

			 
       val repeatingRequest =
                PeriodicWorkRequestBuilder<RefreshDataWorker>(15,TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()
    }

}
```

![WorkerManager%20cd4a543530ce42db92a9eb92e436a5d5/Untitled%201.png](WorkerManager%20cd4a543530ce42db92a9eb92e436a5d5/Untitled%201.png)

- Pass in a repeat interval of 15 with a time unit of TimeUnit.MINUTES.
- NetworkType.UNMETERED is the same as requiring a connection to Wi-Fi.
- setRequiresBatteryNotLow() method is whether device battery should be at an acceptable level for the to run.
- setRequiresCharging() method is whether device should be charging for the to run.
- setRequiresDeviceIdle() method is whether device should be idle for the to run.

3. **WorkManager**

- This class schedules and runs your WorkRequest. WorkManager schedules work requests in a way that spreads out the load on system resources, while honoring the constraints that you specify. You implement the WorkManager in a later task.
- A WorkRequest for repeating work, for example PeriodicWorkRequest, executes multiple times until it is cancelled.
- The first execution happens immediately, or as soon as the given constraints are met.
- The next execution happens during the next period interval. Note that execution might be delayed, because WorkManager is subject to OS battery optimizations, for example when the device is in Doze mode.

```
class DevByteApplication:Application() {

			override fun onCreate() {
        super.onCreate()
        delayedInit()
	    }

			private fun setupRecurringWork(){
					...
					...
					...
				 
			    WorkManager.getInstance().enqueueUniquePeriodicWork(
			            RefreshDataWorker.WORK_NAME,
			            ExistingPeriodicWorkPolicy.KEEP,
			            repeatingRequest)
				}

			private fun delayedInit(){
			        CoroutineScope(Dispatchers.Default).launch {
			            Timber.plant(Timber.DebugTree())
			            setupRecurringWork()
			        }
			    }
}
```

- After you define your WorkRequest, you can schedule it with WorkManager, using the enqueueUniquePeriodicWork() method.
- ExistingPeriodicWorkPolicy.KEEP parameter makes the WorkManager keep the previous periodic work and discard the new work request.

## Homework

---

### **Question 1**

What are the concrete implementations of the **`WorkRequest`** class?   2

▢ **`OneTimeWorkPeriodicRequest`**

▢ **`OneTimeWorkRequest`** and **`PeriodicWorkRequest`**

▢ **`OneTimeWorkRequest`** and **`RecurringWorkRequest`**

▢ **`OneTimeOffWorkRequest`** and **`RecurringWorkRequest`**

### **Question 2**

Which of the following classes does the **`WorkManager`** use to schedule the background task on API 23 and higher? 1

▢ Only **`JobScheduler`**

▢ **`BroadcastReceiver`** and **`AlarmManager`**

▢ **`AlarmManager`** and **`JobScheduler`**

▢ **`Scheduler`** and **`BroadcastReceiver`**

### **Question 3**

Which API do you use to add constraints to a **`WorkRequest`**? 1

▢ **`setConstraints()`**

▢ **`addConstraints()`**

▢ **`setConstraint()`**

▢ **`addConstraintsToWorkRequest()`**