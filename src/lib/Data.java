package lib;

public class Data {
	private String option;
	private String data;
	private String channel;
	
	public Data(String allData) {
		String[] rawData = allData.split(" ");
		this.channel = rawData[0];
		this.option = rawData[1];
		this.data = rawData[2];
	}

	public String getRawData() {
		return channel + " " + option + " " + data;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
