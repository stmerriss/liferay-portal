/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.apio.architect.message.hal.internal;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.test.util.json.MessageMapperTesterBuilder;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.writer.MockWriterUtil;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;

import java.nio.file.Paths;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier Gamarra
 * @author Alejandro Hernández
 */
public class HALMessageMapperTest implements RepresentableManager {

	@Override
	public <T> Optional<Representor<T>> getRepresentorOptional(String name) {
		Optional<Representor<?>> optional =
			MockWriterUtil.getRepresentorOptional(name);

		return optional.map(Unsafe::unsafeCast);
	}

	@Override
	public Map<String, Representor> getRepresentors() {
		return Collections.emptyMap();
	}

	@Test
	public void testHALMessageMappers() {
		HALPageMessageMapper<RootModel> pageMessageMapper =
			new HALPageMessageMapper<>();

		pageMessageMapper.representableManager = this;

		MessageMapperTesterBuilder.path(
			Paths.get("apio-architect-message-hal/src/test/resources")
		).httpHeaders(
			Mockito.mock(HttpHeaders.class)
		).mediaType(
			"application/hal+json"
		).validatePageMessageMapper(
			pageMessageMapper
		).validateSingleModelMessageMapper(
			new HALSingleModelMessageMapper<>()
		);
	}

}