// Copyright 2020 Gabor Kokeny and contributors
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.vaadin.addon.leaflet4vaadin.layer.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.vaadin.addon.leaflet4vaadin.layer.Layer;

/**
 * Used to group several layers and handle them as one. If you add it to the
 * map, any layers added or removed from the group will be added/removed on the
 * map as well. Extends Layer.
 * 
 * @author <strong>Gabor Kokeny</strong> Email:
 *         <a href='mailto=kokeny19@gmail.com'>kokeny19@gmail.com</a>
 * @since 2020-02-06
 * @version 1.1
 */
public class LayerGroup extends Layer {

	private static final long serialVersionUID = 439247482151898231L;
	private List<Layer> layers = new ArrayList<>();

	public LayerGroup(Layer... layers) {
		this(Arrays.asList(layers));
	}

	public LayerGroup(List<Layer> layers) {
		super();
		this.layers = new ArrayList<>(layers);
	}

	/**
	 * precision is the number of decimal places for coordinates. The default value
	 * is 6 places. Returns a GeoJSON representation of the layer group (as a
	 * GeoJSON FeatureCollection, GeometryCollection, or MultiPoint).
	 * 
	 * @param percision the precision
	 */
	public Object toGeoJSON(int precision) {
		throw new UnsupportedOperationException("This method not suported yet.");
	}

	/**
	 * Adds the given layer to the group.
	 * 
	 * @param layer the layer to be add
	 */
	public void addLayer(Layer layer) {
		this.getLayers().add(layer);
	}

	/**
	 * Removes the given layer from the group.
	 * 
	 * @param layer the layer to be remove
	 */
	public void removeLayer(Layer layer) {
		this.removeLayer(layer.getUuid());
	}

	/**
	 * Removes the layer with the given internal ID from the group.
	 * 
	 * @param layerId the id of the layer should remove
	 */
	public void removeLayer(String layerId) {
		getLayer(layerId).ifPresent(l -> getLayers().remove(l));
	}

	/**
	 * Returns true if the given layer is currently added to the group.
	 * 
	 * @param layer layer to be check
	 * @return true if the given layer is currently added to the group.
	 * 
	 * 
	 */
	public boolean hasLayer(Layer layer) {
		return hasLayer(layer.getUuid());
	}

	/**
	 * Returns true if the given internal ID is currently added to the group.
	 * 
	 * @param layerId layer to be check
	 * @return true if the given internal ID is currently added to the group.
	 */
	public boolean hasLayer(String layerId) {
		return getLayer(layerId).isPresent();
	}

	/**
	 * Removes all the layers from the group.
	 */
	public void clearLayers() {
		this.layers.clear();
		execute(this, "clearLayers");
	}

	/**
	 * Calls methodName on every layer contained in this group, passing any
	 * additional parameters. Has no effect if the layers contained do not implement
	 * methodName.
	 */
	public void invoke(String functionName) {
		execute(this, "invoke", functionName);
	}

	/**
	 * Iterates over the layers of the group, optionally specifying context of the
	 * iterator function.
	 */
	public void eachLayer(Consumer<Layer> action) {
		Objects.requireNonNull(action);
		for (Layer layer : this.layers) {
			action.accept(layer);
		}
	}

	/**
	 * Returns the layer with the given internal ID.
	 * 
	 * 
	 * @param layerId layer to be check
	 * @return the layer with the given internal ID.
	 */
	public Optional<Layer> getLayer(String layerId) {
		return this.layers.stream().filter(layer -> layer.getUuid().equals(layerId)).findFirst();
	}

	/**
	 * Returns an array of all the layers added to the group.
	 * 
	 * @return an array of all the layers added to the group.
	 */
	public List<Layer> getLayers() {
		return layers;
	}

	/**
	 * Returns the internal ID for a layer
	 * 
	 * @param layer
	 * @return
	 */
	public String getLayerId(Layer layer) {
		return layer.getUuid();
	}

	/**
	 * Calls setZIndex on every layer contained in this group, passing the z-index.
	 * 
	 * @param zIndex the z-index to be set on each layer
	 */
	public void setZIndex(int zIndex) {
		execute(this, "setZIndex", zIndex);
	}

}
