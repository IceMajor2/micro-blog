package com.demo.blog.blogpostservice.category.assertion.rest;

//public class CategoryRestListAssert extends AbstractListAssert<CategoryRestListAssert, List<CategoryResponse>, CategoryResponse, CategoryResponseAssert> {
//
//    private ResponseEntity<List<CategoryResponse>> responseEntity;
//
//    CategoryRestListAssert(ResponseEntity<List<CategoryResponse>> actual) {
//        super(actual.getBody(), CategoryRestListAssert.class);
//        this.responseEntity = actual;
//    }
//
//    public static CategoryRestListAssert assertThat(ResponseEntity<List<CategoryResponse>> actual) {
//        return new CategoryRestListAssert(actual);
//    }
//
//    public CategoryRestListAssert isSortedByNameAsc() {
//        isNotNull();
//        Assertions.assertThat(actual)
//                .isSortedAccordingTo(CategoryResponseAssert.CATEGORY_RESPONSE_COMPARATOR);
//        return this;
//    }
//
//    public CategoryRestListAssert isValidGetAllResponse(List<CategoryResponse> expected) {
//        isNotNull();
//        HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
//        isSortedByNameAsc();
//        Assertions.assertThat(actual)
//                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
//                .containsAll(expected);
//        return this;
//    }
//
//    @Override
//    protected CategoryResponseAssert toAssert(CategoryResponse actual, String description) {
//        return CategoryResponseAssert.assertThat(actual);
//    }
//
//    @Override
//    protected CategoryRestListAssert newAbstractIterableAssert(Iterable<? extends CategoryResponse> actualIterable) {
//        return null;
//    }
//}
