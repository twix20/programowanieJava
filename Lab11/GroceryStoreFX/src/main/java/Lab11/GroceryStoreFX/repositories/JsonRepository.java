package Lab11.GroceryStoreFX.repositories;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JsonRepository<T> {
	protected Path filePath;
	protected String fileName;
	protected Class<T> type;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public JsonRepository(String fileName, Class<T> type) {
		this.fileName = fileName;
		this.type = type;
		this.filePath = Paths.get(fileName);
	}

	public List<T> getAll() {
		return gson.fromJson(readFile(), new ListOfJson<T>(type));
	}
	
	public void add(T item) {
		List<T> all = getAll();
		all.add(item);
		
		dumpToFile(all);
	}

	protected String readFile() {
	
		String wholeFile = "";
		try {
			wholeFile = new String(Files.readAllBytes(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wholeFile;
	}

	protected void dumpToFile(List<T> allData) {
		String newFileContent = gson.toJson(allData);

		List<String> lines = new ArrayList<>();
		lines.add(newFileContent);

		try {
			Files.write(filePath, lines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class ListOfJson<TModel> implements ParameterizedType {
		private Class<TModel> wrapped;

		public ListOfJson(Class<TModel> wrapper) {
			this.wrapped = wrapper;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { wrapped };
		}

		@Override
		public Type getRawType() {
			return List.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	}
}
