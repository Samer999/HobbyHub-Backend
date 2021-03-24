package com.hobbyhub.models.categories;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class CategoriesHolder implements Categorizable {

  private Set<String> categories;

  public CategoriesHolder() {
    this.categories = new HashSet<>();
  }

  @Override
  public Set<String> getCategories() {
    return new HashSet<>(categories);
  }

  @Override
  public boolean removeCategoryTag(String tag) {
    return categories.remove(tag);
  }

  @Override
  public boolean addCategoryTag(String tag) {
    return categories.add(tag);
  }
}
