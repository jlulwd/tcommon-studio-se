/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orgomg.cwmx.resource.express.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import orgomg.cwmx.resource.express.util.ExpressAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ExpressItemProviderAdapterFactory extends ExpressAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /**
     * This constructs an instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExpressItemProviderAdapterFactory() {
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Database} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DatabaseItemProvider databaseItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Database}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDatabaseAdapter() {
        if (databaseItemProvider == null) {
            databaseItemProvider = new DatabaseItemProvider(this);
        }

        return databaseItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Conjoint} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConjointItemProvider conjointItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Conjoint}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createConjointAdapter() {
        if (conjointItemProvider == null) {
            conjointItemProvider = new ConjointItemProvider(this);
        }

        return conjointItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Program} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProgramItemProvider programItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Program}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createProgramAdapter() {
        if (programItemProvider == null) {
            programItemProvider = new ProgramItemProvider(this);
        }

        return programItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Model} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ModelItemProvider modelItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Model}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createModelAdapter() {
        if (modelItemProvider == null) {
            modelItemProvider = new ModelItemProvider(this);
        }

        return modelItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Variable} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected VariableItemProvider variableItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Variable}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createVariableAdapter() {
        if (variableItemProvider == null) {
            variableItemProvider = new VariableItemProvider(this);
        }

        return variableItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Formula} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FormulaItemProvider formulaItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Formula}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createFormulaAdapter() {
        if (formulaItemProvider == null) {
            formulaItemProvider = new FormulaItemProvider(this);
        }

        return formulaItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.ValueSet} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ValueSetItemProvider valueSetItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.ValueSet}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createValueSetAdapter() {
        if (valueSetItemProvider == null) {
            valueSetItemProvider = new ValueSetItemProvider(this);
        }

        return valueSetItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Relation} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RelationItemProvider relationItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Relation}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createRelationAdapter() {
        if (relationItemProvider == null) {
            relationItemProvider = new RelationItemProvider(this);
        }

        return relationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Worksheet} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorksheetItemProvider worksheetItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Worksheet}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createWorksheetAdapter() {
        if (worksheetItemProvider == null) {
            worksheetItemProvider = new WorksheetItemProvider(this);
        }

        return worksheetItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.Composite} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CompositeItemProvider compositeItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.Composite}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createCompositeAdapter() {
        if (compositeItemProvider == null) {
            compositeItemProvider = new CompositeItemProvider(this);
        }

        return compositeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.SimpleDimension} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimpleDimensionItemProvider simpleDimensionItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.SimpleDimension}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSimpleDimensionAdapter() {
        if (simpleDimensionItemProvider == null) {
            simpleDimensionItemProvider = new SimpleDimensionItemProvider(this);
        }

        return simpleDimensionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.AliasDimension} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AliasDimensionItemProvider aliasDimensionItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.AliasDimension}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createAliasDimensionAdapter() {
        if (aliasDimensionItemProvider == null) {
            aliasDimensionItemProvider = new AliasDimensionItemProvider(this);
        }

        return aliasDimensionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.AggMap} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AggMapItemProvider aggMapItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.AggMap}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createAggMapAdapter() {
        if (aggMapItemProvider == null) {
            aggMapItemProvider = new AggMapItemProvider(this);
        }

        return aggMapItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.AggMapComponent} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AggMapComponentItemProvider aggMapComponentItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.AggMapComponent}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createAggMapComponentAdapter() {
        if (aggMapComponentItemProvider == null) {
            aggMapComponentItemProvider = new AggMapComponentItemProvider(this);
        }

        return aggMapComponentItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link orgomg.cwmx.resource.express.PreComputeClause} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PreComputeClauseItemProvider preComputeClauseItemProvider;

    /**
     * This creates an adapter for a {@link orgomg.cwmx.resource.express.PreComputeClause}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createPreComputeClauseAdapter() {
        if (preComputeClauseItemProvider == null) {
            preComputeClauseItemProvider = new PreComputeClauseItemProvider(this);
        }

        return preComputeClauseItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class) || (((Class<?>)type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void dispose() {
        if (databaseItemProvider != null) databaseItemProvider.dispose();
        if (conjointItemProvider != null) conjointItemProvider.dispose();
        if (programItemProvider != null) programItemProvider.dispose();
        if (modelItemProvider != null) modelItemProvider.dispose();
        if (variableItemProvider != null) variableItemProvider.dispose();
        if (formulaItemProvider != null) formulaItemProvider.dispose();
        if (valueSetItemProvider != null) valueSetItemProvider.dispose();
        if (relationItemProvider != null) relationItemProvider.dispose();
        if (worksheetItemProvider != null) worksheetItemProvider.dispose();
        if (compositeItemProvider != null) compositeItemProvider.dispose();
        if (simpleDimensionItemProvider != null) simpleDimensionItemProvider.dispose();
        if (aliasDimensionItemProvider != null) aliasDimensionItemProvider.dispose();
        if (aggMapItemProvider != null) aggMapItemProvider.dispose();
        if (aggMapComponentItemProvider != null) aggMapComponentItemProvider.dispose();
        if (preComputeClauseItemProvider != null) preComputeClauseItemProvider.dispose();
    }

}
