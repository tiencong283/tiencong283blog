$(document).foundation();

//  get the categoryID corresponding to "to whom" of the action triggered
function findCategoryID(event) {
    return $(event.target).closest("tr").children(":first-child").text();
}

// construct the endpoint
function findEndpoint(event) {
    return `${window.location.href}/${findCategoryID(event)}`;
}

let $editCategoryForm = $("#editCategoryForm");
let $categoryIDIp = $editCategoryForm.find("input[name='categoryID']");
let $nameIp = $editCategoryForm.find("input[name='name']");
let $urlSlugIp = $editCategoryForm.find("input[name='urlSlug']");

// populate category info while opening the edit dialog
$(".editCategoryBtn").click(event => {
    $.getJSON(findEndpoint(event), function (data) {
        $categoryIDIp.val(data['categoryID']);
        $nameIp.val(data['name']);
        $urlSlugIp.val(data['urlSlug']);
        $("#editCategoryDialog").foundation('open');    // open the dialog
    });
});

// save edit category
$editCategoryForm.submit(event => {
    let endpoint = `${window.location.href}/${$categoryIDIp.val()}`;
    $.ajax({
        url: endpoint,
        method: "PUT",
        data: {
            name: $nameIp.val(),
            urlSlug: $urlSlugIp.val()
        },
        complete: () => location.reload(),
    });
    event.preventDefault();
});

// delete category
$(".deleteCategoryBtn").click(event => {
    $.ajax({
        url: findEndpoint(event),
        method: "DELETE",
        complete: () => location.reload(),
    });
});