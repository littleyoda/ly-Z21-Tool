package wrapper;

public abstract class Z21Callback<T> implements Runnable {

		private T userdata;

		public T getUserdata() {
			return userdata;
		}

		public void setUserdata(T userdata) {
			this.userdata = userdata;
		}
		

}
