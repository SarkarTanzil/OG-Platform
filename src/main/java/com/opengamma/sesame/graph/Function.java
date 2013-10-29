/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.opengamma.OpenGammaRuntimeException;

/**
*
*/
public final class Function<T> extends Node {

  private final Constructor<? extends T> _constructor;
  private final List<Node> _arguments;
  // TODO map of ? -> Method for building invokers? what's the key? do we need OutputKey(outputName, targetType)?
  // TODO how do we get the invokers out of the graph build process?

  public Function(Constructor<? extends T> constructor, List<Node> arguments) {
    _constructor = constructor;
    _arguments = ImmutableList.copyOf(arguments);
  }

  @Override
  T create(Map<Class<?>, Object> infrastructure) {
    try {
      List<Object> arguments = Lists.newArrayListWithCapacity(_arguments.size());
      for (Node argument : _arguments) {
        arguments.add(argument.create(infrastructure));
      }
      return _constructor.newInstance(arguments.toArray());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new OpenGammaRuntimeException("Failed to create of " + _constructor.getDeclaringClass().getName(), e);
    }
  }

  public Class<? extends T> getType() {
    return _constructor.getDeclaringClass();
  }

  public List<Node> getArguments() {
    return _arguments;
  }
}
