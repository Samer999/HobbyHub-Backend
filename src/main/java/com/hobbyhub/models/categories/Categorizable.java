package com.hobbyhub.models.categories;

import java.util.Set;

public interface Categorizable {
  Set<String> getCategories();
  void setCategories(Set<String> categories);
  boolean removeCategoryTag(String tag);
  boolean addCategoryTag(String tag);
}
