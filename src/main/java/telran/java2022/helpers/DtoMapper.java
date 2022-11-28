package telran.java2022.helpers;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DtoMapper {
	final ModelMapper modelMapper;
	final Map<Class<?>, Class<?>> castMap;

	@SuppressWarnings("unchecked")
	public <T> T map(Object sourceObject, Class<T> targetClass) {
		return (T) modelMapper.map(sourceObject, getTargetClass(sourceObject.getClass()));
	}

	private Class<?> getTargetClass(Class<?> sourceClass) {
		return castMap.get(sourceClass);
	}

	public static DtoMapperBuilder of(ModelMapper modelMapper) {
		return new DtoMapperBuilder(modelMapper);
	}

	@RequiredArgsConstructor
	public static class DtoMapperBuilder {
		final ModelMapper modelMapper;
		final Map<Class<?>, Class<?>> castMap = new HashMap<>();

		public DtoMapperBuilder withEntry(Class<?> firstClass, Class<?> secondClass) {
			castMap.put(firstClass, secondClass);
			castMap.put(secondClass, firstClass);
			return this;
		}

		public DtoMapper build() {
			return new DtoMapper(modelMapper, castMap);
		}
	}
}
