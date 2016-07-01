package view;

// Source https://github.com/lestard/structured-list/blob/master/client-javafx/src/main/java/eu/lestard/structuredlist/util/RecursiveTreeItem.java

/*******************************************************************************
 * Copyright 2016 Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.util.Callback;

public class RecursiveTreeItem<T> extends TreeItem<T> {

	private Callback<T, ObservableList<T>> childrenFactory;

	public RecursiveTreeItem(Callback<T, ObservableList<T>> func) {
		this(null, func);
	}

	public RecursiveTreeItem(final T value, Callback<T, ObservableList<T>> func) {
		this(value, (Node) null, func);
	}

	public RecursiveTreeItem(final T value, Node graphic, Callback<T, ObservableList<T>> func) {
		super(value, graphic);

		this.childrenFactory = func;

		if (value != null) {
			addChildrenListener(value);
		}

		valueProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				addChildrenListener(newValue);
			}
		});
	}

	private void addChildrenListener(T value) {
		final ObservableList<T> children = childrenFactory.call(value);

		children.forEach(child -> RecursiveTreeItem.this.getChildren()
				.add(new RecursiveTreeItem<>(child, getGraphic(), childrenFactory)));

		children.addListener((ListChangeListener<T>) change -> {
			while (change.next()) {
				if (change.wasRemoved()) {
					change.getRemoved().forEach(t -> {
						final List<TreeItem<T>> itemsToRemove = RecursiveTreeItem.this.getChildren().stream()
								.filter(treeItem -> treeItem.getValue().equals(t)).collect(Collectors.toList());

						RecursiveTreeItem.this.getChildren().removeAll(itemsToRemove);
					});
				}
				if (change.wasAdded()) {
					change.getAddedSubList().forEach(t -> RecursiveTreeItem.this.getChildren()
							.add(new RecursiveTreeItem<>(t, getGraphic(), childrenFactory)));
				}

			}
		});
	}
}
